package pl.mycar.technicalexaminationservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechnicalExaminationServiceApplication implements CommandLineRunner {
  private final RabbitTemplate rabbitTemplate;
  static final String topicExchangeName = "spring-boot-exchange";


  @Autowired
  public TechnicalExaminationServiceApplication(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(TechnicalExaminationServiceApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(topicExchangeName, "foo.bar.baz", "hello from rabbitmq!");

  }
}
