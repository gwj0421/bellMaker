package ics.mgs.config.web;

import ics.mgs.config.property.CorsProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {
    private final CorsProperty corsProperty;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(corsProperty.getAllowedOrigins());
        config.setAllowedHeaders(corsProperty.getAllowedHeaders());
        config.setAllowedMethods(corsProperty.getAllowedMethods());
        config.setMaxAge(corsProperty.getMaxAge());

        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
