package spiis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpiisServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiisServerApplication.class, args);
    }
}
