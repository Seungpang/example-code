package me.seungpang.ordersapp.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.seungpang.ordersapp.domain.Order;
import me.seungpang.ordersapp.domain.Revenue;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static Serde<Order> orderSerdes() {
        JsonSerializer<Order> jsonSerializer = new JsonSerializer<>(objectMapper);
        JsonDeserializer<Order> jsonDeserializer = new JsonDeserializer<>(Order.class, objectMapper);

        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }

    public static Serde<Revenue> revenueSerdes() {
        JsonSerializer<Revenue> jsonSerializer = new JsonSerializer<>(objectMapper);
        JsonDeserializer<Revenue> jsonDeserializer = new JsonDeserializer<>(Revenue.class, objectMapper);

        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }
}
