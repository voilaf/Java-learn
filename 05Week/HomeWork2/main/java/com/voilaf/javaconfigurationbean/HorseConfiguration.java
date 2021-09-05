package com.voilaf.javaconfigurationbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HorseConfiguration {

    @Bean
    public Horse createHorse() {
        return new Horse();
    }
}
