package ics.mgs.config.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "aws.s3")
public class S3property {
    private final String region;
    private final String accessKey;
    private final String secretKey;
    private final String bucket;

    @ConstructorBinding
    public S3property(String region, String accessKey, String secretKey, String bucket) {
        this.region = region;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }
}
