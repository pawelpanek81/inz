package pl.mycar.technicalexaminationservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import pl.mycar.technicalexaminationservice.rabbitmq.mail.MailMessageProducer;
import pl.mycar.technicalexaminationservice.rabbitmq.push.PushMessageProducer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"pl.mycar.config", "pl.mycar.technicalexaminationservice"})
@EnableSwagger2
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class TechnicalExaminationServiceApplication implements CommandLineRunner {
//  private PushMessageProducer pushMessageProducer;
//  private MailMessageProducer mailMessageProducer;

  public static void main(String[] args) {
    SpringApplication.run(TechnicalExaminationServiceApplication.class, args);
  }

//  public TechnicalExaminationServiceApplication(PushMessageProducer pushMessageProducer, MailMessageProducer mailMessageProducer) {
//    this.pushMessageProducer = pushMessageProducer;
//    this.mailMessageProducer = mailMessageProducer;
//  }

  @Override
  public void run(String... args) throws Exception {
//    pushMessageProducer.sendMessage("push test");
//    mailMessageProducer.sendMessage("mail test");
  }
}
