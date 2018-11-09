package pl.mycar.insuranceservice.rabbitmq.mail;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailMessageProducer {
  private static final String mailExchangeName = "mail-exchange";
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public MailMessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String message) {
    rabbitTemplate.convertAndSend(mailExchangeName, "aqm.mail", message);
  }
}
