/**
 * <KAUSTUBH_CHAUDHARI>, <001218494>, <chaudhari.k@husky.neu.edu>
 * <AKSHAY_NAKHAWA>, <001665873>, <nakhawa.a@husky.neu.edu>
 * <PRACHI_SAXENA>, <001220709>, <saxena.pr@husky.neu.edu>
 * <AKHILA_KUNCHE>, <001251306>, <kunche.a@husky.neu.edu>

 **/



package com.csye6225.demo.auth;

import com.google.gson.JsonObject;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class BasicAuthEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
    PrintWriter writer = response.getWriter();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "unauthorized");
    writer.println(jsonObject.toString());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    setRealmName("csye6225");
    super.afterPropertiesSet();
  }

}
