package com.github.microprograms.module_micro_api_aliyun_oss;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.microprograms.module_micro_api_aliyun_oss.http_server.HttpServer;

public class Activator implements BundleActivator {
	private static final Logger log = LoggerFactory.getLogger(Activator.class);
	private HttpServer httpServer;

	@Override
	public void start(BundleContext context) throws Exception {
		log.info("module-micro-api-aliyun-oss starting ...");
		httpServer = new HttpServer(context);
		httpServer.start();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log.info("module-micro-api-aliyun-oss stopping ...");
		httpServer.stop();
	}
}
