package ics.mgs.config.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "cors")
public class CorsProperty {
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private Long maxAge;

    @ConstructorBinding
    public CorsProperty(List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders, Long maxAge) {
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
        this.maxAge = maxAge;
    }
}
