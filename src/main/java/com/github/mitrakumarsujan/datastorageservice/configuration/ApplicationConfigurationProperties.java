package com.github.mitrakumarsujan.datastorageservice.configuration;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@Configuration
@ConfigurationProperties(prefix = "spring")
public class ApplicationConfigurationProperties {

	private Map<String, String> application;

	public void setApplication(Map<String, String> application) {
		if (this.application == null) {
			this.application = Collections.unmodifiableMap(application);
		}
	}

	public String getProperty(String property) {
		return application.get(property);
	}

	public String getProperty(String property, String defaultValue) {
		return application.getOrDefault(property, defaultValue);
	}

	public String getProperty(String property, Supplier<String> defaultValue) {
		return application.getOrDefault(property, defaultValue.get());
	}

	public String getUploadBaseDirectory() {
		return getProperty("upload-directory");
	}
}
