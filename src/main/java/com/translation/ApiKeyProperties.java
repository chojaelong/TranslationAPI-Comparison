package com.translation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "apikey")
public class ApiKeyProperties {

    private final String naverClientId;
    private final String naverClientSecret;
}
