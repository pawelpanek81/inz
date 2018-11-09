package pl.mycar.insuranceservice.rabbitmq.push;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PushMessageProducer {
  private static final String mailExchangeName = "push-exchange";
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public PushMessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String message) {
    rabbitTemplate.convertAndSend(mailExchangeName, "aqm.push", message);
  }
}
