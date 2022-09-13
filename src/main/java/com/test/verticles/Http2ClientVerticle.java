package com.test.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.tracing.TracingPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Http2ClientVerticle extends AbstractVerticle {
    private static final Logger LOG = LogManager.getLogger(Http2ClientVerticle.class);
    @Override
    public void start() throws Exception {
        HttpClientOptions httpClientOptions = new HttpClientOptions().setDefaultHost("127.0.0.1").setDefaultPort(9998)
                .setSsl(true)
                .setKeepAlive(true)
                .setTcpKeepAlive(true)
                .setTracingPolicy(TracingPolicy.ALWAYS)
                .setTryUseCompression(true)
                .setProtocolVersion(HttpVersion.HTTP_2)
                .setVerifyHost(false)
                .setUseAlpn(true)
                .setTrustAll(true);
        HttpClient httpClient = vertx.createHttpClient(httpClientOptions);
        httpClient.request(HttpMethod.GET,"/success",ar->{
            if(ar.succeeded()){
                ar.result().send("hi").onSuccess(rsp->{
                    LOG.info("/success statusCode:{}",rsp.statusCode());
                    rsp.body().onSuccess(bodyBuffer->{
                        LOG.info("/success body:{}",bodyBuffer.toString());
                    }).onFailure(e->{
                        e.printStackTrace();
                    });
                }).onFailure(e->{
                    e.printStackTrace();
                });
            }
        });

        vertx.setTimer(1*1000,v->{
            httpClient.request(HttpMethod.GET,"/timeout",ar->{
                if(ar.succeeded()){
                    ar.result().setTimeout(1000).send("hi").onSuccess(rsp->{
                        LOG.info("/timeout statusCode:{}",rsp.statusCode());
                        rsp.body().onSuccess(bodyBuffer->{
                            LOG.info("/timeout body:{}",bodyBuffer.toString());
                        }).onFailure(e->{
                            e.printStackTrace();
                        });
                    }).onFailure(e->{
                       LOG.error("rsp fail:{}",e.getMessage(),e);
                    });
                }else {
                    ar.cause().printStackTrace();
                }
            });
        });
    }
}
