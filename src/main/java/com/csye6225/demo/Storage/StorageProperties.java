package com.csye6225.demo.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("StorageProperties")
@Component
public class StorageProperties {

    private String location = "/home/prachi/Documents";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}

