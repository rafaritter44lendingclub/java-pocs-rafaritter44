package com.lc.ritter.java.pocs.logger;

public class ConsoleLogger extends Logger {
    protected ConsoleLogger(final LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    protected void log(final String message) {
        System.out.println(message);
    }
}
