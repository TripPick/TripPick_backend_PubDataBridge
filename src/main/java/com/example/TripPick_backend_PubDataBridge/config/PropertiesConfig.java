package com.example.TripPick_backend_PubDataBridge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "trip-pick.api")
public class PropertiesConfig {
    private String serviceKey;
}
