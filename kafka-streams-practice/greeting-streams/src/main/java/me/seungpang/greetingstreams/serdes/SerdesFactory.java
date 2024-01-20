package me.seungpang.greetingstreams.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.seungpang.greetingstreams.domain.Greeting;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    public static Serde<Greeting> greetingSerde() {
        return new GreetingSerdes(objectMapper);
    }

    public static Serde<Greeting> greetingSerdeUsingGenerics() {
        JsonSerializer<Greeting> jsonSerializer = new JsonSerializer<>(objectMapper);
        JsonDeserializer<Greeting> jsonDeserializer = new JsonDeserializer<>(Greeting.class, objectMapper);

        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }
}
