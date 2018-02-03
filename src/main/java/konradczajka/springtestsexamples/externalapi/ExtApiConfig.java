package konradczajka.springtestsexamples.externalapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExtApiConfig {
    @Bean
    RestTemplate createExtApiTemplate() {
        return new RestTemplate();
    }
}
