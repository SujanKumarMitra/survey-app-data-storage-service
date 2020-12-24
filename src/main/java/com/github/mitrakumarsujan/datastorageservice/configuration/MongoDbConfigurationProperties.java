package com.github.mitrakumarsujan.datastorageservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties("spring.data.mongodb")
public class MongoDbConfigurationProperties {

    private String host;
    private int port;
    private String database;

    public MongoDbConfigurationProperties() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoDbConfigurationProperties that = (MongoDbConfigurationProperties) o;
        return port == that.port && Objects.equals(host, that.host) && Objects.equals(database, that.database);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, database);
    }

    @Override
    public String toString() {
        return "MongoDbConfigurationProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", database='" + database + '\'' +
                '}';
    }
}
