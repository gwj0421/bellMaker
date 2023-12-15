package ics.mgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MgsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MgsApplication.class, args);
    }
}
