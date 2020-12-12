package com.github.mitrakumarsujan.datastorageservice.configuration;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@Configuration
@EnableConfigurationProperties({UploadsConfiguration.class})
public class ApplicationConfiguration {
}
