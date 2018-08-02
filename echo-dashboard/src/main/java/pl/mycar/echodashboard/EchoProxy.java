package pl.mycar.echodashboard;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EchoService")
@RibbonClient(name = "EchoService")
@Component
public interface EchoProxy {

  @GetMapping("echo/{echo}")
  String echo(@PathVariable(value = "echo") String echo);
}
