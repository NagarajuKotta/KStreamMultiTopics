package com.kgn.kstream.multitopic.service;

import com.kgn.kstream.multitopic.entity.PublishDetails;
import com.kgn.kstream.multitopic.entity.Client;
import com.kgn.kstream.multitopic.entity.Employee;
import com.kgn.kstream.multitopic.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class EmpMainDetailsPublishService {
    @Autowired
    KafkaTemplate template;

    @Value("${spring.kafka.source-topic1}")
    private String employeeTopic;
    @Value("${spring.kafka.source-topic2}")
    private String clientTopic;
    @Value("${spring.kafka.source-topic3}")
    private String projectTopic;


    public String publishEmpManiDetails(PublishDetails publishDetails){
        log.info("Publishing messages to topics..");
        String key = UUID.randomUUID().toString();
        ProducerRecord<String, Employee> employeeRecord = new ProducerRecord<>(employeeTopic,key, publishDetails.getEmployee());
        if(publishDetails.getClient()!=null){
            ProducerRecord<String, Client> clientRecord = new ProducerRecord<>(clientTopic,key, publishDetails.getClient());

            template.send(clientRecord);
        }
        if(publishDetails.getProject()!=null){
            ProducerRecord<String, Project> projectRecord = new ProducerRecord<>(projectTopic,key, publishDetails.getProject());

            template.send(projectRecord);
        }
        template.send(employeeRecord);


        log.info("Published messages to topics..{},{},{}",employeeTopic,clientTopic,projectTopic);
        return key;
    }

}
