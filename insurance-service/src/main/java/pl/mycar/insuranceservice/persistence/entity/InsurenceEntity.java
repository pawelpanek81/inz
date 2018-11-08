package pl.mycar.insuranceservice.persistence.entity;

import java.time.LocalDate;

public class InsurenceEntity {
  private LocalDate fromDate;
  private LocalDate toDate;
  private Double cost;
  // type oc ac
  private Long carId;
  private String username;
  private String description;
}
