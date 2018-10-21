package pl.mycar.notificationservice.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RabbitCommons {

  public static SimpleMessageListenerContainer addListenerToQueue(ConnectionFactory connectionFactory,
                                                                  MessageListenerAdapter listenerAdapter,
                                                                  String mailQueueName) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(mailQueueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }
}
