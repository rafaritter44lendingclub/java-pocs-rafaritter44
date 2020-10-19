package com.github.rafaritter44.java_pocs.spring.security.authorization_server.config;

import static org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX;
import static org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters.RESTEASY_USE_CONTAINER_FORM_PARAMS;

import javax.naming.CompositeName;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.keycloak.services.filters.KeycloakSessionServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedKeycloakConfig {
	
	@Bean
	public ServletRegistrationBean<HttpServlet30Dispatcher> keycloakJaxRsApplication(
			KeycloakServerProperties properties, DataSource dataSource) throws NamingException {
		NamingManager.setInitialContextFactoryBuilder(env -> environment -> new InitialContext() {
			@Override
			public Object lookup(Name name) {
				return lookup(name.toString());
			}
			@Override
			public Object lookup(String name) {
				if ("spring/datasource".equals(name)) {
					return dataSource;
				}
				return null;
			}
			@Override
			public NameParser getNameParser(String name) {
				return CompositeName::new;
			}
			@Override
			public void close() {
				// NOOP
			}
		});
		EmbeddedKeycloakApplication.setKeycloakServerProperties(properties);
		final ServletRegistrationBean<HttpServlet30Dispatcher> servlet =
				new ServletRegistrationBean<>(new HttpServlet30Dispatcher());
		servlet.addInitParameter("javax.ws.rs.Application", EmbeddedKeycloakApplication.class.getName());
		servlet.addInitParameter(RESTEASY_SERVLET_MAPPING_PREFIX, properties.getContextPath());
		servlet.addInitParameter(RESTEASY_USE_CONTAINER_FORM_PARAMS, "true");
		servlet.addUrlMappings(properties.getContextPath() + "/*");
		servlet.setLoadOnStartup(1);
		servlet.setAsyncSupported(true);
		return servlet;
	}
	
	@Bean
	public FilterRegistrationBean<KeycloakSessionServletFilter> keycloakSessionManagement(
			KeycloakServerProperties properties) {
		final FilterRegistrationBean<KeycloakSessionServletFilter> filter = new FilterRegistrationBean<>();
		filter.setName("Keycloak Session Management");
		filter.setFilter(new KeycloakSessionServletFilter());
		filter.addUrlPatterns(properties.getContextPath() + "/*");
		return filter;
	}

}
