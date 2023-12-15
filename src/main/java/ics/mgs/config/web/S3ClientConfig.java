package ics.mgs.config.web;

import ics.mgs.config.property.S3property;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {
    private final S3property s3property;

    @Bean
    public S3AsyncClient s3AsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return S3AsyncClient.builder()
                .region(Region.of(s3property.getRegion()))
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        if (s3property.getAccessKey().isBlank()) {
            return DefaultCredentialsProvider.create();
        } else {
            return () -> AwsBasicCredentials.create(
                    s3property.getAccessKey(),
                    s3property.getSecretKey());
        }
    }

}
