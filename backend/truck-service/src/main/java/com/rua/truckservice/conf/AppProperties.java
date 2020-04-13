package com.rua.truckservice.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private int locationHistorySize;

    public int getLocationHistorySize() {
        return locationHistorySize;
    }

    public void setLocationHistorySize(int size) {
        this.locationHistorySize = size;
    }
}
