package pl.mycar.technicalexaminationservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mycar.technicalexaminationservice.rabbitmq.mail.MailMessageProducer;
import pl.mycar.technicalexaminationservice.rabbitmq.push.PushMessageProducer;

@SpringBootApplication
public class TechnicalExaminationServiceApplication implements CommandLineRunner {
  private PushMessageProducer pushMessageProducer;
  private MailMessageProducer mailMessageProducer;

  public static void main(String[] args) {
    SpringApplication.run(TechnicalExaminationServiceApplication.class, args);
  }

  public TechnicalExaminationServiceApplication(PushMessageProducer pushMessageProducer, MailMessageProducer mailMessageProducer) {
    this.pushMessageProducer = pushMessageProducer;
    this.mailMessageProducer = mailMessageProducer;
  }

  @Override
  public void run(String... args) throws Exception {
    pushMessageProducer.sendMessage("push test");
    mailMessageProducer.sendMessage("mail test");
  }
}
