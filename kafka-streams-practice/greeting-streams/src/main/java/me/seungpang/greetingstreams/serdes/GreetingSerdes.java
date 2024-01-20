package me.seungpang.greetingstreams.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.seungpang.greetingstreams.domain.Greeting;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class GreetingSerdes implements Serde<Greeting> {

    private final ObjectMapper objectMapper;

    public GreetingSerdes(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializer<Greeting> serializer() {
        return new GreetingSerializer(objectMapper);
    }

    @Override
    public Deserializer<Greeting> deserializer() {
        return new GreetingDeserializer(objectMapper);
    }
}
