package me.seungpang.greetingstreams.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;

@Slf4j
public class StreamsDeserializationExceptionHandler implements DeserializationExceptionHandler {

    int errorCounter = 0;

    @Override
    public DeserializationHandlerResponse handle(final ProcessorContext context, final ConsumerRecord<byte[], byte[]> record, final Exception e) {
        log.error("Exception is : {} , and the Kafka record is : {} ", e.getMessage(), record, e);
        log.info("errorCounter : {}", errorCounter);
        if (errorCounter < 2) {
            errorCounter++;
            return DeserializationHandlerResponse.CONTINUE;
        }
        return DeserializationHandlerResponse.FAIL;
    }

    @Override
    public void configure(final Map<String, ?> map) {

    }
}
