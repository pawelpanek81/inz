package pl.mycar.zuulserver.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class SwaggerDocumentation implements SwaggerResourcesProvider {

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    resources.add(swaggerResource("account-service", "/api/account/v2/api-docs", "2.0"));
    resources.add(swaggerResource("map-service", "/api/map/v2/api-docs", "2.0"));
    resources.add(swaggerResource("car-service", "/api/car/v2/api-docs", "2.0"));
    resources.add(swaggerResource("examination-service", "/api/examination/v2/api-docs", "2.0"));
    resources.add(swaggerResource("insurance-service", "/api/insurance/v2/api-docs", "2.0"));
    resources.add(swaggerResource("notification-service", "/api/notification/v2/api-docs", "2.0"));

    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
