package me.seungpang.greetingstreams.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;

@Slf4j
public class StreamsProcessorCustomErrorHandler implements StreamsUncaughtExceptionHandler {

    @Override
    public StreamThreadExceptionResponse handle(final Throwable e) {
        log.error("Exception in the Application : {} ", e.getMessage(), e);
        if (e instanceof StreamsException) {
            var cause = e.getCause();
            if (cause.getCause().equals("Transient Error")) {
                return StreamThreadExceptionResponse.REPLACE_THREAD;
            }
        }
        return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
    }
}
