package com.lc.ritter.java.pocs.logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncLogger extends Logger {
    private final Logger logger;
    private final ExecutorService executor;

    protected AsyncLogger(final Logger logger) {
        super(logger.logLevel);
        this.logger = logger;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void log(final String message) {
        executor.submit(() -> logger.log(message));
    }
}
