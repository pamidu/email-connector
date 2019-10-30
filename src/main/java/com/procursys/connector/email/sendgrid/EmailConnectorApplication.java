package com.procursys.connector.email.sendgrid;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailConnectorApplication {

	@Value("${tomcat.ajp.port}")
	private int ajpPort;

	@Value("${tomcat.ajp.secure}")
	private boolean ajpSecure;

	@Value("${tomcat.ajp.allowtrace}")
	private boolean allowAjpTrace;

	@Value("${tomcat.ajp.max.threads}")
	private String ajpMaxThreads;

	@Value("${tomcat.ajp.accept.count}")
	private String ajpAcceptCount;

	private static final String ACCEPT_COUNT = "acceptCount";

	private static final String MAX_THREADS = "maxThreads";

	@Bean
	public TomcatServletWebServerFactory servletContainer() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

		Connector ajpConnector = new Connector("AJP/1.3");
		ajpConnector.setPort(ajpPort);
		ajpConnector.setSecure(ajpSecure);
		ajpConnector.setAllowTrace(allowAjpTrace);
		ajpConnector.setScheme("ajp");
		ajpConnector.setRedirectPort(ajpPort);
		ajpConnector.setProperty(MAX_THREADS, ajpMaxThreads);
		ajpConnector.setProperty(ACCEPT_COUNT, ajpAcceptCount);
		tomcat.addAdditionalTomcatConnectors(ajpConnector);

		return tomcat;
	}

	public static void main(String[] args) {
		SpringApplication.run(EmailConnectorApplication.class, args);
	}

}
