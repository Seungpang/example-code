package me.seungpang.greetingstreams.serdes;

import me.seungpang.greetingstreams.domain.Greeting;
import org.apache.kafka.common.serialization.Serde;

public class SerdesFactory {

    public static Serde<Greeting> greetingSerde() {
        return new GreetingSerdes();
    }
}
