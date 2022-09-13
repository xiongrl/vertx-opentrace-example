package com.test.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOG = LogManager.getLogger(MainVerticle.class);

    @Override
    public void start() throws Exception {
        LOG.info("start");
        vertx.deployVerticle("com.test.verticles.Http2ServerVerticle",new DeploymentOptions());
        vertx.deployVerticle("com.test.verticles.Http2ClientVerticle",new DeploymentOptions());
    }

    @Override
    public void stop() throws Exception {
    }
}
