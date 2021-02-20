package spiis.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class ServerConfig {

    // Allow web browsers to contact us even if they are on a different host
    // E.g. the browser can be on spiis.net, but still send requests to api.spiis.net
    // This is safe because we use tokens for authentication
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("POST", "GET", "DELETE", "OPTIONS", "PUT");
            }
        };
    }
}