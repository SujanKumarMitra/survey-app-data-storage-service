package com.github.mitrakumarsujan.datastorageservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-12-05
 */
@ConfigurationProperties(prefix = "app.uploads")
public class UploadsConfiguration {

    private String baseDir;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public String toString() {
        return "UploadsConfiguration{" +
                "baseDir='" + baseDir + '\'' +
                '}';
    }

}
