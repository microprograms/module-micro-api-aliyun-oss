package com.github.microprograms.module_micro_api_aliyun_oss;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.microprograms.micro_api_runtime.annotations.MicroApi;
import com.github.microprograms.micro_api_runtime.model.Api;
import com.github.microprograms.module_micro_api_aliyun_oss.api.CreatePresignedUrl;
import com.github.microprograms.module_micro_api_aliyun_oss.http_server.HttpServer;

public class Activator implements BundleActivator {
	private static final Logger log = LoggerFactory.getLogger(Activator.class);
	private BundleContext context;
	private HttpServer httpServer;

	@Override
	public void start(BundleContext context) throws Exception {
		log.info("module-micro-api-aliyun-oss starting ...");

		this.context = context;
		Config.load(context);
		Config config = Config.get();
		httpServer = new HttpServer(config);
		httpServer.start();

		_registerApi(new CreatePresignedUrl(config));
	}

	private void _registerApi(Api api) throws InstantiationException, IllegalAccessException {
		Dictionary<String, String> properties = new Hashtable<>();
		properties.put("apiName", api.getClass().getDeclaredAnnotation(MicroApi.class).name());
		context.registerService(api.getClass().getName(), api, properties);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log.info("module-micro-api-aliyun-oss stopping ...");
		httpServer.stop();
	}
}
