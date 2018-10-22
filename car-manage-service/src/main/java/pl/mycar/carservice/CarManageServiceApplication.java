package pl.mycar.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"pl.mycar.config", "pl.mycar.carservice"})
@EnableSwagger2
public class CarManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarManageServiceApplication.class, args);
	}
}
