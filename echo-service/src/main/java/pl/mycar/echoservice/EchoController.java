package pl.mycar.echoservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

  @GetMapping("/echo/{echo}")
  public String echo(@PathVariable String echo) {
    return "Echo: " + echo;
  }
}
