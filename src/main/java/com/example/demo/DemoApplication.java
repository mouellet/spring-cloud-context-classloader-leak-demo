package com.example.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// Logger initialization is deferred in case a ordered
		// LogServletContextInitializer is being used
		this.logger = LogFactory.getLog(getClass());
		WebApplicationContext rootAppContext = createRootApplicationContext(
				servletContext);
		if (rootAppContext != null) {
			servletContext.addListener(new ContextLoaderListener(rootAppContext) {
				@Override
				public void contextInitialized(ServletContextEvent event) {
					// no-op because the application context is already initialized
				}
				@Override
				public void contextDestroyed(ServletContextEvent event) {
					ApplicationContext parentAppContext = rootAppContext.getParent();
					super.contextDestroyed(event);
					if (parentAppContext != null) {
						SpringApplication.exit(parentAppContext, () -> 0);
					}
					parentAppContext = null;
				}
			});
		}
		else {
			this.logger.debug("No ContextLoaderListener registered, as "
					+ "createRootApplicationContext() did not "
					+ "return an application context");
		}
	}

}
