package me.seungpang.greetingstreams.topology;

import lombok.extern.slf4j.Slf4j;
import me.seungpang.greetingstreams.domain.Greeting;
import me.seungpang.greetingstreams.serdes.SerdesFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

@Slf4j
public class GreetingsTopology {

    public static final String GREETINGS = "greetings";
    public static final String GREETINGS_UPPERCASE = "greetings_uppercase";

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        var greetingsStream = getCustomGreetingKStream(streamsBuilder);

        greetingsStream
                .print(Printed.<String, Greeting>toSysOut().withLabel("greetingsStream"));

        //var modifiedStream = exploreOperators(greetingsStream);

        KStream<String, Greeting> modifiedStream = exploreErrors(greetingsStream);

        modifiedStream
                .print(Printed.<String, Greeting>toSysOut().withLabel("modifiedStream"));

        modifiedStream
                .to(GREETINGS_UPPERCASE, Produced.with(Serdes.String(), SerdesFactory.greetingSerdeUsingGenerics()));

        return streamsBuilder.build();
    }

    private static KStream<String, Greeting> exploreErrors(final KStream<String, Greeting> greetingsStream) {
        return greetingsStream
                .mapValues((readOnlyKey, value) -> {
                    if (value.message().equals("Transient Error")) {
                        try {
                            throw new IllegalStateException(value.message());
                        } catch (Exception e) {
                            log.error("Exception in exploreErrors : {} ", e.getMessage(), e);
                            return null;
                        }

                    }
                    return new Greeting(value.message().toUpperCase(), value.timeStamp());
                })
                .filter((key, value) -> key != null && value != null);
    }

    private static KStream<String, Greeting> exploreOperators(final KStream<String, Greeting> greetingsStream) {
        return greetingsStream
                .filter((key, value) -> value.message().length() > 5)
                .mapValues((readOnlyKey, value) -> new Greeting(value.message().toUpperCase(), value.timeStamp()));
    }

    private static KStream<String, Greeting> getCustomGreetingKStream(final StreamsBuilder streamsBuilder) {
        var greetingsStream = streamsBuilder.
                stream(GREETINGS
                        , Consumed.with(Serdes.String(), SerdesFactory.greetingSerdeUsingGenerics())
                );

        return greetingsStream;
    }
}
