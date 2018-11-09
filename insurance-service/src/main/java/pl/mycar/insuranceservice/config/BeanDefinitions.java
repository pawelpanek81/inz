package pl.mycar.insuranceservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinitions {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
