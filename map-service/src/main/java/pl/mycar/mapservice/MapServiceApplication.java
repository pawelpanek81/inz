package pl.mycar.mapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MapServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(MapServiceApplication.class, args);
  }
}
