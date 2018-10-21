package pl.mycar.notificationservice.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

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
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                           MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(mailQueueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(MailMessageReceiver mailMessageReceiver) {
    return new MessageListenerAdapter(mailMessageReceiver, "receiveMessage");
  }
}
