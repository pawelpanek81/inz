package pl.mycar.notificationservice.rabbitmq.mail;

import org.springframework.stereotype.Component;

@Component
public class MailMessageReceiver {

  private void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    System.out.println("Sending mail...");
  }

}