package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.FinalEmpDetails;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class FinalEmpDetailsSerdes extends Serdes.WrapperSerde<FinalEmpDetails>{
    public FinalEmpDetailsSerdes(Serializer<FinalEmpDetails> serializer, Deserializer<FinalEmpDetails> deserializer) {
        super(serializer, deserializer);
    }
    public static Serde<FinalEmpDetails> finalEmpDetailsSerdes(){
        JsonSerializer<FinalEmpDetails> finalEmpDetailsJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<FinalEmpDetails> finalEmpDetailsJsonDeserializer = new JsonDeserializer<>(FinalEmpDetails.class);
        return Serdes.serdeFrom(finalEmpDetailsJsonSerializer, finalEmpDetailsJsonDeserializer);

    }
}
