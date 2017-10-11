/**
 * <KAUSTUBH_CHAUDHARI>, <001218494>, <chaudhari.k@husky.neu.edu>
 * <AKSHAY_NAKHAWA>, <001665873>, <nakhawa.a@husky.neu.edu>
 * <PRACHI_SAXENA>, <001220709>, <saxena.pr@husky.neu.edu>
 * <AKHILA_KUNCHE>, <001251306>, <kunche.a@husky.neu.edu>

 **/

package com.csye6225.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private BasicAuthEntryPoint basicAuthEntryPoint;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(basicAuthEntryPoint);
  }

  @Bean
  public HttpSessionStrategy httpSessionStrategy() {
    return new HeaderHttpSessionStrategy();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user").password(bCryptPasswordEncoder.encode("password")).roles("USER").build());
    return manager;
  }
}
