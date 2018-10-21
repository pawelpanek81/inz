package pl.mycar.notificationservice.rabbitmq.push;

import org.springframework.stereotype.Component;

@Component
public class PushMessageReceiver {

  private void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    System.out.println("Sending push...");
  }

}