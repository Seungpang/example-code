package me.seungpang.greetingstreams.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.greetingstreams.domain.Greeting;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class GreetingDeserializer implements Deserializer<Greeting> {

    private final ObjectMapper objectMapper;

    public GreetingDeserializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Greeting deserialize(final String topic, final byte[] data) {
        try {
            return objectMapper.readValue(data, Greeting.class);
        } catch (IOException e) {
            log.error("IOException : {} ", e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Exception : {} ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
