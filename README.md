# vertx-opentrace-example
jdk version 17

```java
	private static void initJaeger(){
		System.setProperty("JAEGER_REPORTER_LOG_SPANS", "true");
		System.setProperty("JAEGER_SAMPLER_TYPE", "const");
		System.setProperty("JAEGER_SAMPLER_PARAM", "1");
		System.setProperty("JAEGER_AGENT_HOST", "JAEGER_AGENT_HOST"); //need change host
		System.setProperty("JAEGER_AGENT_PORT", "6831");
		System.setProperty("JAEGER_SERVICE_NAME", "test");
	}
```

main method com.test.ServerLauncher#main


![image](https://user-images.githubusercontent.com/4568603/189851581-1167eaae-7ea6-4932-90c7-09f2c9790874.png)
