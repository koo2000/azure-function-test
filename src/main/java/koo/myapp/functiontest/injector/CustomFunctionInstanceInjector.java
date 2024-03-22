package koo.myapp.functiontest.injector;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import com.microsoft.azure.functions.spi.inject.FunctionInstanceInjector;

import koo.myapp.functiontest.app.Application;

public class CustomFunctionInstanceInjector implements FunctionInstanceInjector {
	private static ConfigurableApplicationContext APPLICATION_CONTEXT;

	public CustomFunctionInstanceInjector() {

	}

	@Override
	public <T> T getInstance(Class<T> clazz) throws Exception {
		initialize();

		Map<String, T> obj = APPLICATION_CONTEXT.getBeansOfType(clazz);

		return obj.entrySet().iterator().next().getValue();
	}

	/**
	 * Create a static Application Context instance shared between multiple function
	 * invocations.
	 */
	private static void initialize() {
		synchronized (FunctionInstanceInjector.class) {
			if (APPLICATION_CONTEXT == null) {
				Class<?> springConfigurationClass = Application.class;
				APPLICATION_CONTEXT = springApplication(springConfigurationClass).run();
			}
		}
	}

	private static SpringApplication springApplication(Class<?> configurationClass) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		return application;
	}

}