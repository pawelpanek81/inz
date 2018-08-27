package pl.mycar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.mycar.config.cors.CustomCorsFilter;
import pl.mycar.config.jwt.JwtTokenAuthenticationFilter;
import pl.mycar.jwtconfig.JwtConfig;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@ComponentScan("pl.mycar.jwtconfig")
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

  private JwtConfig jwtConfig;
  private CustomCorsFilter customCorsFilter;

  @Autowired
  public SecurityTokenConfig(JwtConfig jwtConfig, CustomCorsFilter customCorsFilter) {
    this.jwtConfig = jwtConfig;
    this.customCorsFilter = customCorsFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().and().csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
          .exceptionHandling().authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
          .addFilterBefore(customCorsFilter, ChannelProcessingFilter.class)
          .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
          .anyRequest().permitAll();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
