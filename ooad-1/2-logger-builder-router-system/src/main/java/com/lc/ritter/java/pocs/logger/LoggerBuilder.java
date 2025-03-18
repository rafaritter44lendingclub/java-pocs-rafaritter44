package com.lc.ritter.java.pocs.logger;

public class LoggerBuilder {
    private Class<? extends Logger> loggerClass;
    private LogLevel logLevel;
    private ExecutionMode executionMode;

    public LoggerBuilder withLoggerClass(final Class<? extends Logger> loggerClass) {
        this.loggerClass = loggerClass;
        return this;
    }

    public LoggerBuilder withLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public LoggerBuilder withExecutionMode(final ExecutionMode executionMode) {
        this.executionMode = executionMode;
        return this;
    }

    public Logger build() {
        try {
            final var logger = loggerClass.getDeclaredConstructor(LogLevel.class).newInstance(logLevel);
            return switch (executionMode) {
                case SYNC -> logger;
                case ASYNC -> new AsyncLogger(logger);
            };
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
