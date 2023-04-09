package stark.dataworks.boot.autoconfig.minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configurations for MinioClient.
 */
@Component
@ConfigurationProperties("dataworks.easy-minio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EasyMinioProperties
{
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
