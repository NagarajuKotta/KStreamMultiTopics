package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.Project;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class ProjectSerdes extends Serdes.WrapperSerde<Project> {
    public ProjectSerdes(Serializer<Project> serializer, Deserializer<Project> deserializer) {
        super(serializer, deserializer);
    }
    public static Serde<Project> projectSerdes(){
        JsonSerializer<Project> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Project> jsonDeserializer = new JsonDeserializer<>(Project.class);
        return Serdes.serdeFrom(jsonSerializer,jsonDeserializer);
    }
}
