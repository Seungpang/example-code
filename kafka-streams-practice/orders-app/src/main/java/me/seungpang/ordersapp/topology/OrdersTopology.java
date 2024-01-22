package me.seungpang.ordersapp.topology;

import lombok.extern.slf4j.Slf4j;
import me.seungpang.ordersapp.domain.Order;
import me.seungpang.ordersapp.serdes.SerdesFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;

@Slf4j
public class OrdersTopology {
    public static final String ORDERS = "orders";
    public static final String STORES = "stores";

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        var ordersStream = streamsBuilder
                .stream(ORDERS,
                        Consumed.with(Serdes.String(), SerdesFactory.orderSerdes())
                );

        ordersStream
                .print(Printed.<String, Order>toSysOut().withLabel("orders"));

        return streamsBuilder.build();
    }
}
