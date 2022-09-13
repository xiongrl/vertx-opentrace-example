package com.test;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.Launcher;

/**
 *
 */
public class ServerLauncher extends Launcher {
	private static final String DEFALUT_VERTX_OPTION_FILE_NAME = "vertx_options.json";
	private static final String DEFALUT_LOG4J2 = "log4j2.xml";

	/**
	 * 设置日志
	 */
	private static void initLog4j2() {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");// 将ignite的日志默认改成用lo4j2管理
		System.setProperty("LOG4J_CONFIGURATION_FILE", DEFALUT_LOG4J2);
		InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
		System.setProperty("vertx.logger-delegate-factory-class-name","io.vertx.core.logging.Log4j2LogDelegateFactory");
	}

	private static void initJaeger(){
		System.setProperty("JAEGER_REPORTER_LOG_SPANS", "true");
		System.setProperty("JAEGER_SAMPLER_TYPE", "const");
		System.setProperty("JAEGER_SAMPLER_PARAM", "1");
		System.setProperty("JAEGER_AGENT_HOST", "JAEGER_AGENT_HOST");
		System.setProperty("JAEGER_AGENT_PORT", "6831");
		System.setProperty("JAEGER_SERVICE_NAME", "test");
	}

	public static void start(Launcher launcher) {
		initLog4j2();
		initJaeger();
		String[] targs = new String[6];
		targs[0] = "run";
		targs[1] = "com.test.verticles.MainVerticle";
		targs[2] = "-options";
		targs[3] = """
				{
				  "tracingOptions": {}
				}
				""";
		targs[4] = "-instances";
		targs[5] = String.valueOf(1);
		launcher.dispatch(targs);
	}

	public static void main(String[] args) {
		start(new ServerLauncher());
	}
}
