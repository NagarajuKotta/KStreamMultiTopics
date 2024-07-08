package com.kgn.kstream.multitopic.serdes;

import com.kgn.kstream.multitopic.entity.Client;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class ClientSerdes extends Serdes.WrapperSerde<Client> {
    public ClientSerdes(Serializer<Client> serializer, Deserializer<Client> deserializer) {
        super(serializer, deserializer);
    }
    public static Serde<Client> clientSerdes(){
        JsonSerializer<Client> clientJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Client> clientJsonDeserializer = new JsonDeserializer<>(Client.class);
        return Serdes.serdeFrom(clientJsonSerializer, clientJsonDeserializer);
    }
}
