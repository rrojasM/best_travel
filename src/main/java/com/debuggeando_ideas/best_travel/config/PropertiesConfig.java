package com.debuggeando_ideas.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(@PropertySource(value = "classpath:configs/api_currency.properties"))
public class PropertiesConfig {

}
