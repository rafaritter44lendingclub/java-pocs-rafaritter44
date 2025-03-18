package com.lc.ritter.java.pocs.logger;

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    FATAL(5);

    private final int severity;

    LogLevel(final int severity) {
        this.severity = severity;
    }

    public boolean shouldLog(final LogLevel logLevel) {
        if (logLevel == null) {
            return false;
        }
        return this.severity <= logLevel.severity;
    }
}
