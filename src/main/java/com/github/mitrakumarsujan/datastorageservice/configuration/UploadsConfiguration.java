package com.github.mitrakumarsujan.datastorageservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;





/**
 * @author Sujan Kumar Mitra
 * @since 2020-12-05
 */
@ConfigurationProperties(prefix = "app.uploads")
public class UploadsConfiguration {

    private final String baseDir;

    @ConstructorBinding
    public UploadsConfiguration(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getBaseDir() {
        return baseDir;
    }

    @Override
    public String toString() {
        return "UploadsConfiguration{" +
                "baseDir='" + baseDir + '\'' +
                '}';
    }
}
