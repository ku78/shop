package com.geekbrains.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("integration/http-orders-gateway.xml")
public class OrderIntegrationConfig {
}
