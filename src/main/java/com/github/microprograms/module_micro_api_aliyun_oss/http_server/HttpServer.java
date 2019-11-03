package com.github.microprograms.module_micro_api_aliyun_oss.http_server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.microprograms.module_micro_api_aliyun_oss.http_server.OssServlet.Config;

public class HttpServer {
	private static final Logger log = LoggerFactory.getLogger(HttpServer.class);
	private static final String property_key_port = "module_micro_api_aliyun_oss.port";
	private static final String property_key_callback_url = "module_micro_api_aliyun_oss.callback_url";
	private static final String property_key_access_id = "module_micro_api_aliyun_oss.access_id";
	private static final String property_key_access_key = "module_micro_api_aliyun_oss.access_key";
	private static final String property_key_endpoint = "module_micro_api_aliyun_oss.endpoint";
	private static final String property_key_bucket = "module_micro_api_aliyun_oss.bucket";
	private static final String property_key_dir = "module_micro_api_aliyun_oss.dir";

	private BundleContext context;
	private Server server;

	public HttpServer(BundleContext context) {
		this.context = context;
	}

	public synchronized void start() throws Exception {
		String port = context.getProperty(property_key_port);
		log.info("BundleContext Property {} = {}", property_key_port, port);

		server = new Server(Integer.valueOf(port));
		ServletContextHandler webApiContext = new ServletContextHandler();
		webApiContext.setContextPath("/");
		webApiContext.addServlet(new ServletHolder(new OssServlet(_buildConfig())), "/*");
		webApiContext.setSessionHandler(new SessionHandler());

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { webApiContext, new DefaultHandler() });
		server.setHandler(handlers);
		server.start();
	}

	private Config _buildConfig() {
		Config config = new Config();

		String callbackUrl = context.getProperty(property_key_callback_url);
		log.info("BundleContext Property {} = {}", property_key_callback_url, callbackUrl);
		config.setCallbackUrl(callbackUrl);

		String accessId = context.getProperty(property_key_access_id);
		log.info("BundleContext Property {} = {}", property_key_access_id, accessId);
		config.setAccessId(accessId);

		String accessKey = context.getProperty(property_key_access_key);
		log.info("BundleContext Property {} = {}", property_key_access_key, accessId);
		config.setAccessKey(accessKey);

		String endpoint = context.getProperty(property_key_endpoint);
		log.info("BundleContext Property {} = {}", property_key_endpoint, accessId);
		config.setEndpoint(endpoint);

		String bucket = context.getProperty(property_key_bucket);
		log.info("BundleContext Property {} = {}", property_key_bucket, accessId);
		config.setBucket(bucket);

		String dir = context.getProperty(property_key_dir);
		log.info("BundleContext Property {} = {}", property_key_dir, accessId);
		config.setDir(dir);

		return config;
	}

	public synchronized void stop() throws Exception {
		server.stop();
	}
}
