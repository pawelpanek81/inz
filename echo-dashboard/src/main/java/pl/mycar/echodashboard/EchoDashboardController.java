package pl.mycar.echodashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class EchoDashboardController {
  @Autowired
  private EchoProxy echoProxy;

  @GetMapping("/dashboard/feign/{echo}")
  public String echoProxy(@PathVariable String echo) {
    return echoProxy.echo(echo);
  }
}
