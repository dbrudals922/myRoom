package com.swh.db;

import java.io.IOException;

import org.eclipse.jetty.server.AbstractNCSARequestLog;
import org.eclipse.jetty.util.log.Logger;

public class RequestLogFactory {

    private Logger logger;

    public RequestLogFactory(Logger logger) {
        this.logger = logger;
    }

    AbstractNCSARequestLog create() {
        return new AbstractNCSARequestLog(null) {
            @Override
            protected boolean isEnabled() {
                return true;
            }

            @Override
            public void write(String s) throws IOException {
                logger.info(s);
            }
        };
    }
}
