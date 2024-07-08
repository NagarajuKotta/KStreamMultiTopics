package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.EmpProjectKey;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.Deserializer;

public class EmpProjectKeySerde extends Serdes.WrapperSerde<EmpProjectKey> {

    public EmpProjectKeySerde(Serializer<EmpProjectKey> serializer, Deserializer<EmpProjectKey> deserializer) {
        super(serializer, deserializer);
    }
    public static Serde<EmpProjectKey> empProjectKeySerdes(){
        JsonSerializer<EmpProjectKey> empProjectKeyJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<EmpProjectKey>  empProjectKeyJsonDeserializer= new JsonDeserializer<>(EmpProjectKey.class);
        return Serdes.serdeFrom(empProjectKeyJsonSerializer, empProjectKeyJsonDeserializer);

    }
}
