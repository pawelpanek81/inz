package pl.mycar.notificationservice.rabbitmq.push;

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
public class PushRabbitConfiguration {

  private static final String mailExchangeName = "push-exchange";
  private static final String mailQueueName = "push-queue";

  @Bean
  Queue pushQueue() {
    return new Queue(mailQueueName, false);
  }

  @Bean
  TopicExchange pushExchange() {
    return new TopicExchange(mailExchangeName);
  }

  @Bean
  Binding binding(Queue pushQueue, TopicExchange pushExchange) {
    return BindingBuilder.bind(pushQueue).to(pushExchange).with("aqm.push.#");
  }

  @Bean
  SimpleMessageListenerContainer pushContainer(ConnectionFactory connectionFactory,
                                               MessageListenerAdapter listenerAdapter) {
    return RabbitCommons.addListenerToQueue(connectionFactory, listenerAdapter, mailQueueName);
  }



  @Bean
  MessageListenerAdapter listenerAdapter(PushMessageReceiver pushMessageReceiver) {
    return new MessageListenerAdapter(pushMessageReceiver, "receiveMessage");
  }
}
