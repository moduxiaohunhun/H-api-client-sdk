package study.hapiclientsdk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("hapi.client")
@Data
@ComponentScan
public class HApiClientConfig {

    private String accessKey;

    private String secretKey;



}
