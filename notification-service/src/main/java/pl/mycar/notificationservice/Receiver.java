package pl.mycar.notificationservice;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

  private void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
  }

}
