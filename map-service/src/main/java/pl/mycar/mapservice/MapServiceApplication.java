package pl.mycar.mapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"pl.mycar.config", "pl.mycar.mapservice"})
@EnableSwagger2
public class MapServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(MapServiceApplication.class, args);
  }
}
