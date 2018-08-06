package pl.mycar.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"pl.mycar.authserver",
    "pl.mycar.jwtconfig",
    "pl.mycar.accountservice.config"})
@EnableJpaRepositories({"pl.mycar.accountservice.persistence.repository"})
@EntityScan({"pl.mycar.accountservice.persistence.entity"})
public class AuthServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }
}
