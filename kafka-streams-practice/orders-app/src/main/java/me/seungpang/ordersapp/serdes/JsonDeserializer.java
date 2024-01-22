package me.seungpang.ordersapp.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class JsonDeserializer<T> implements Deserializer<T> {

    private final Class<T> destinationClass;
    private final ObjectMapper objectMapper;

    public JsonDeserializer(Class<T> destinationClass, final ObjectMapper objectMapper) {
        this.destinationClass = destinationClass;
        this.objectMapper = objectMapper;
    }

    @Override
    public T deserialize(final String topic, final byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, destinationClass);
        } catch (IOException e) {
            log.error("IOException in Deserializer : {} ", e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Exception  in Deserializer : {} ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
