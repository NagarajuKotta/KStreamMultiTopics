package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.EmpDetails;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class EmpDetailsSerdes extends Serdes.WrapperSerde<EmpDetails> {
    public EmpDetailsSerdes(Serializer<EmpDetails> serializer, Deserializer<EmpDetails> deserializer) {
        super(serializer, deserializer);
    }

    public static Serde<EmpDetails> empDetailsSerdes(){
        JsonSerializer<EmpDetails> empDetailsJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<EmpDetails> empDetailsJsonDeserializer = new JsonDeserializer<>(EmpDetails.class);
        return Serdes.serdeFrom(empDetailsJsonSerializer, empDetailsJsonDeserializer);
    }
}
