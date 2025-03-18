package com.lc.ritter.java.pocs.logger;

import static com.lc.ritter.java.pocs.logger.ExecutionMode.*;
import static com.lc.ritter.java.pocs.logger.LogLevel.*;

public class LoggingSystem {
    public static void main(final String[] args) {
        final var logger = new LoggerBuilder()
                .withLoggerClass(ConsoleLogger.class)
                .withLogLevel(INFO)
                .withExecutionMode(SYNC)
                .build();
        final var asyncLogger = new LoggerBuilder()
                .withLoggerClass(ConsoleLogger.class)
                .withLogLevel(ERROR)
                .withExecutionMode(ASYNC)
                .build();

        asyncLogger.log(ERROR, "This is an ERROR message from the async logger");
        asyncLogger.log(FATAL, "This is a FATAL message from the async logger");
        logger.log(INFO, "This is an INFO message from the sync logger");
        logger.log(WARN, "This is a WARN message from the sync logger");
        logger.log(ERROR, "This is an ERROR message from the sync logger");
        logger.log(FATAL, "This is a FATAL message from the sync logger");

        asyncLogger.log(WARN, "This won't be logged");
        logger.log(DEBUG, "This won't be logged");
    }
}
