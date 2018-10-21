package pl.mycar.notificationservice.rabbitmq.mail;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mycar.notificationservice.rabbitmq.RabbitCommons;

@Configuration
public class MailRabbitConfiguration {

  private static final String mailExchangeName = "mail-exchange";
  private static final String mailQueueName = "mail-queue";

  @Bean
  Queue mailQueue() {
    return new Queue(mailQueueName, false);
  }

  @Bean
  TopicExchange mailExchange() {
    return new TopicExchange(mailExchangeName);
  }

  @Bean
  Binding binding(Queue mailQueue, TopicExchange mailExchange) {
    return BindingBuilder.bind(mailQueue).to(mailExchange).with("aqm.mail.#");
  }

  @Bean
  SimpleMessageListenerContainer mailContainer(ConnectionFactory connectionFactory,
                                               MessageListenerAdapter mailListenerAdapter) {
    return RabbitCommons.addListenerToQueue(connectionFactory, mailListenerAdapter, mailQueueName);
  }

  @Bean
  MessageListenerAdapter mailListenerAdapter(MailMessageReceiver mailMessageReceiver) {
    return new MessageListenerAdapter(mailMessageReceiver, "receiveMessage");
  }
}
