package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.Employee;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class EmployeeSerdes extends Serdes.WrapperSerde<Employee> {
    public EmployeeSerdes(Serializer<Employee> serializer, Deserializer<Employee> deserializer) {
        super(serializer, deserializer);
    }
    public static Serde<Employee> employeeSerdes(){
        JsonSerializer<Employee> employeeJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Employee> employeeJsonDeserializer = new JsonDeserializer<>(Employee.class);
        return Serdes.serdeFrom(employeeJsonSerializer, employeeJsonDeserializer);

    }
}
