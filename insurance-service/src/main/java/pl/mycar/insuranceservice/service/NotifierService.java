package pl.mycar.insuranceservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mycar.insuranceservice.feign.CarClient;
import pl.mycar.insuranceservice.model.database.InsuranceType;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;
import pl.mycar.insuranceservice.persistence.repository.InsuranceRepository;
import pl.mycar.insuranceservice.rabbitmq.mail.MailMessageProducer;
import pl.mycar.insuranceservice.rabbitmq.push.PushMessageProducer;
import pl.mycar.insuranceservice.rabbitmq.push.model.PushNotificationDTO;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotifierService {
  private PushMessageProducer pushMessageProducer;
  private MailMessageProducer mailMessageProducer;
  private InsuranceRepository insuranceRepository;


  public NotifierService(PushMessageProducer pushMessageProducer,
                         MailMessageProducer mailMessageProducer,
                         InsuranceRepository insuranceRepository,
                         CarClient carClient) {
    this.pushMessageProducer = pushMessageProducer;
    this.mailMessageProducer = mailMessageProducer;
    this.insuranceRepository = insuranceRepository;
  }

  @Scheduled(cron = "0 0 1 * * ?")
  public void sendExaminationExpiresPushNotification() {
    System.out.println("Wysyłam....");
    List<InsuranceEntity> insurances = insuranceRepository.findAll();
    insurances.stream()
        .filter(e -> LocalDate.now().plusDays(7).equals(e.getToDate()))
        .forEach(
            e -> {
              Long carId = e.getCarId();
              String username = e.getUsername();
              String type = e.getType().equals(InsuranceType.THIRD_PARTY) ? "OC" : "AC";
              PushNotificationDTO notification = new PushNotificationDTO(
                  "Koniec ważności ubezpieczenia " + type,
                  "Uwaga, zbliża się koniec ważności ubezpieczenia" + type +
                      ". Ubezpieczenie kończy się " + e.getToDate(),
                  username,
                  carId
              );
              try {
                String message = null;
                message = new ObjectMapper().writeValueAsString(notification);
                pushMessageProducer.sendMessage(message);
              } catch (JsonProcessingException e1) {
                e1.printStackTrace();
              }
            }
        );

  }
}
