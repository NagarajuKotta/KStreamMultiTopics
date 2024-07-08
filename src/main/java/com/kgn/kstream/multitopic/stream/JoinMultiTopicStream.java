package com.kgn.kstream.multitopic.stream;

import com.kgn.kstream.multitopic.entity.*;
import com.kgn.kstream.multitopic.serdes.ClientSerdes;
import com.kgn.kstream.multitopic.serdes.EmpProjectKeySerde;
import com.kgn.kstream.multitopic.serdes.FinalEmpDetailsSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import static com.kgn.kstream.multitopic.serdes.EmpDetailsSerdes.empDetailsSerdes;
import static com.kgn.kstream.multitopic.serdes.EmployeeSerdes.employeeSerdes;
import static com.kgn.kstream.multitopic.serdes.ProjectSerdes.projectSerdes;

@Slf4j
@Service
public class JoinMultiTopicStream {

    @Value("${spring.kafka.source-topic1}")
    private String employeeTopic;

    @Value("${spring.kafka.source-topic2}")
    private String clientTopic;

    @Value("${spring.kafka.source-topic3}")
    private String projectTopic;

    @Value("${spring.kafka.destination-topic}")
    private String destinationTopic;

    @Bean
    public KStream<EmpProjectKey, FinalEmpDetails> processMultiTopics(StreamsBuilder streamsBuilder) {

        // Define the input topics with String keys
        KStream<String, Employee> employeeStream = streamsBuilder.stream(employeeTopic, Consumed.with(Serdes.String(), new JsonSerde<>(Employee.class)));
        KTable<String, Client> clientTable = streamsBuilder.table(clientTopic, Consumed.with(Serdes.String(), new JsonSerde<>(Client.class)));
        KTable<String, Project> projectTable = streamsBuilder.table(projectTopic, Consumed.with(Serdes.String(), new JsonSerde<>(Project.class)));

        // Re-key the employeeStream to use clientId
        KStream<Integer, Employee> rekeyedEmployeeStream = employeeStream
                .selectKey((key, value) -> value.getClientId());
        rekeyedEmployeeStream.peek((k, v) -> log.info("Key:{}==value=>{}", k, v));

        // Re-key the clientTable to use clientId
        KTable<Integer, Client> rekeyedClientTable = clientTable
                .toStream()
                .selectKey((key, value) -> value.getClientId())
                .toTable(Materialized.<Integer, Client>as(Stores.inMemoryKeyValueStore("RekeyedClientTable"))
                        .withKeySerde(Serdes.Integer())
                        .withValueSerde(ClientSerdes.clientSerdes()));
        rekeyedClientTable.toStream().peek((k, v) -> log.info("clientKey={} ==>value={}", k, v));

        // Re-key the projectTable to use projectId
        KTable<Integer, Project> rekeyedProjectTable = projectTable
                .toStream()
                .selectKey((key, value) -> value.getProjectId())
                .toTable(Materialized.<Integer, Project>as(Stores.inMemoryKeyValueStore("RekeyedProjectTable"))
                        .withKeySerde(Serdes.Integer())
                        .withValueSerde(projectSerdes()));
        rekeyedProjectTable.toStream().peek((k, v) -> log.info("ProjectKey={} ==>value={}", k, v));

        // Join employee stream with rekeyed client table
        KStream<Integer, EmpDetails> employeeClientStream = rekeyedEmployeeStream.join(rekeyedClientTable,
                (employee, client) -> new EmpDetails(employee.getEmpId(), employee.getEmpName(), employee.getCompanyName(), client.getClientName(), "", client.getClientId(), employee.getProjectId()),
                Joined.with(Serdes.Integer(), employeeSerdes(), ClientSerdes.clientSerdes()));
        employeeClientStream.peek((k, v) -> log.info("EmployeeClientStream Key:{} ==> Value: {} ", k, v));

        // Commented below block of code since I want to publish destination topic key as combination of empId and projectId
        // Re-key the employeeClientStream to use projectId
        KStream<Integer, EmpDetails> rekeyedEmployeeClientStream = employeeClientStream
                .selectKey((key, empDetails) -> empDetails.getProjectId());
        rekeyedEmployeeClientStream.peek((k, v) -> log.info("RekeyedEmployeeClientStream Key: {} ==> Value: {}", k, v));

        // Join with rekeyed project table
        KStream<Integer, FinalEmpDetails> finalStream = rekeyedEmployeeClientStream.join(rekeyedProjectTable,
                (empDetails, project) -> new FinalEmpDetails(empDetails.getEmpId(), empDetails.getEmpName(), empDetails.getCompanyName(), empDetails.getClientName(), project.getProjectName()),
                Joined.with(Serdes.Integer(), empDetailsSerdes(), projectSerdes()));
        finalStream.peek((k, v) -> log.info("FinalStream Key: {} ==> Value: {} ", k, v));

        // re-key combination of empId and projectId
        KStream<EmpProjectKey, FinalEmpDetails> finalEmpDetailsKStream = finalStream.selectKey((k, e) -> new EmpProjectKey(e.getEmpId(), k));
        finalEmpDetailsKStream.peek((k, v) -> log.info("Final Result  key:{}, value:{}", k, v));
        // Output the final joined stream to an output topic
        finalEmpDetailsKStream.to(destinationTopic, Produced.with(EmpProjectKeySerde.empProjectKeySerdes(), FinalEmpDetailsSerdes.finalEmpDetailsSerdes()));

        return finalEmpDetailsKStream;
    }
}
