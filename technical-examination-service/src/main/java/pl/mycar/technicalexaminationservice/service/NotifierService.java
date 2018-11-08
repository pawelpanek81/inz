package pl.mycar.technicalexaminationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mycar.technicalexaminationservice.feign.CarClient;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationRepository;
import pl.mycar.technicalexaminationservice.rabbitmq.mail.MailMessageProducer;
import pl.mycar.technicalexaminationservice.rabbitmq.push.PushMessageProducer;
import pl.mycar.technicalexaminationservice.rabbitmq.push.model.PushNotificationDTO;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotifierService {
  private PushMessageProducer pushMessageProducer;
  private MailMessageProducer mailMessageProducer;
  private ExaminationRepository examinationRepository;


  public NotifierService(PushMessageProducer pushMessageProducer,
                         MailMessageProducer mailMessageProducer,
                         ExaminationRepository examinationRepository,
                         CarClient carClient) {
    this.pushMessageProducer = pushMessageProducer;
    this.mailMessageProducer = mailMessageProducer;
    this.examinationRepository = examinationRepository;
  }

  @Scheduled(cron = "0 1 * * *")
  public void sendExaminationExpiresPushNotification() {
    System.out.println("Wysyłam....");
    List<ExaminationEntity> examinations = examinationRepository.findAll();
    examinations.stream()
        .filter(e -> e.getExaminationDate().equals(LocalDate.now().minusYears(1).minusDays(7)))
        .forEach(
            e -> {
              Long carId = e.getCarId();
              String username = e.getUsername();
              PushNotificationDTO notification = new PushNotificationDTO(
                  "Koniec ważności badania technicznego",
                  "Uwaga, zbliża się koniec ważności badania technicznego.",
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
