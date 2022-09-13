package com.test.verticles;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.Http2Settings;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PfxOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Http2ServerVerticle extends AbstractVerticle {
    private static final Logger LOG = LogManager.getLogger(Http2ServerVerticle.class);
    @Override
    public void start() throws Exception {
        HttpServerOptions httpServerOptions = new HttpServerOptions().setTcpKeepAlive(true).setLogActivity(true)
                .setTcpNoDelay(true).setIdleTimeout(300)
                .setCompressionSupported(true)
                .setIdleTimeoutUnit(TimeUnit.SECONDS)
                .setUseAlpn(true)
                .setSsl(true)
                .setInitialSettings(new Http2Settings().setMaxConcurrentStreams(1000))
                .setPfxKeyCertOptions(new PfxOptions().setPath("test.p12").setPassword("test123456"))
                .setPort(9998);

        HttpServer httpServer = vertx.createHttpServer(httpServerOptions);
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/success").handler(routingContext ->{
            routingContext.response().setStatusCode(200).end("success");
        });

        router.get().handler(routingContext ->{
            LOG.info("path:{}",routingContext.request().path());
            vertx.setTimer(3*1000,v->{
                routingContext.response().setStatusCode(200).end("ok");
            });
        });

        httpServer.requestHandler(router).listen().onSuccess(server->{
            LOG.info("httpServer start success!");
        }).onFailure(e->{
            e.printStackTrace();
        });
    }
}
