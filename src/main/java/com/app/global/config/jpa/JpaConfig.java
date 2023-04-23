package com.app.global.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class JpaConfig {

    private final HttpServletRequest httpServletRequest;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(httpServletRequest);
    }

}