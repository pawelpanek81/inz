package pl.mycar.insuranceservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mycar.insuranceservice.model.dto.CreateInsuranceDTO;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;

@Configuration
public class BeanDefinitions {

  @Bean
  ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(new PropertyMap<CreateInsuranceDTO, InsuranceEntity>() {
      @Override
      protected void configure() {
        skip(destination.getId());
      }
    });
    return modelMapper;
  }
}
