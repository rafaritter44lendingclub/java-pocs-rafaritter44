package com.lc.ritter.java.pocs.logger;

public abstract class Logger {
    protected final LogLevel logLevel;

    protected Logger(final LogLevel logLevel) {
        this.logLevel = logLevel == null ? LogLevel.INFO : logLevel;
    }

    protected abstract void log(final String message);

    public void log(final LogLevel logLevel, final String message) {
        if (!this.logLevel.shouldLog(logLevel)) {
            return;
        }
        final var formattedMessage = "[%s] %s".formatted(logLevel, message);
        log(formattedMessage);
    }
}
