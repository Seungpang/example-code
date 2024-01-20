package me.seungpang.greetingstreams.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

public class GreetingsTopology {

    public static final String GREETINGS = "greetings";
    public static final String GREETINGS_UPPERCASE = "greetings_uppercase";

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> greetingsStream = streamsBuilder.
                stream(GREETINGS
//                        , Consumed.with(Serdes.String(), Serdes.String())
                );

        greetingsStream
                .print(Printed.<String, String>toSysOut().withLabel("greetingsStream"));

        KStream<String, String> modifiedStream = greetingsStream
                .filter((key, value) -> value.length() > 5)
                .mapValues((readOnlyKey, value) -> value.toUpperCase());

        modifiedStream
                .print(Printed.<String, String>toSysOut().withLabel("modifiedStream"));

        modifiedStream
                .to(GREETINGS_UPPERCASE
//                        , Produced.with(Serdes.String(), Serdes.String())
                );

        return streamsBuilder.build();
    }
}
