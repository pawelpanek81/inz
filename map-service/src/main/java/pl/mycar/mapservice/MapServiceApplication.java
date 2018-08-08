package pl.mycar.mapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan({"pl.mycar.jwt", "pl.mycar.mapservice"})
public class MapServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(MapServiceApplication.class, args);
  }
}
