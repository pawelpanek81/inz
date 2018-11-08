package pl.mycar.technicalexaminationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"pl.mycar.config", "pl.mycar.technicalexaminationservice"})
@EnableSwagger2
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableScheduling
public class TechnicalExaminationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechnicalExaminationServiceApplication.class, args);
  }
}
