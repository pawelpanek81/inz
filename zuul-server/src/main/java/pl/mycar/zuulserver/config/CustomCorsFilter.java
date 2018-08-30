package pl.mycar.zuulserver.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, PATCH, OPTIONS, DELETE");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Version");
    httpServletResponse.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Location");
    httpServletResponse.setHeader("Content-Type", "text/html");
    final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    if (!httpServletRequest.getMethod().equals("OPTIONS")) {
      chain.doFilter(request, response);
    } else {
      // do not chain options requests
    }
  }

  @Override
  public void destroy() {

  }
}
