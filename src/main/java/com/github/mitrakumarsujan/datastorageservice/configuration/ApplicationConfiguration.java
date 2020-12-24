package com.github.mitrakumarsujan.datastorageservice.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@Configuration
@EnableConfigurationProperties({UploadsConfiguration.class})
public class ApplicationConfiguration {
}
