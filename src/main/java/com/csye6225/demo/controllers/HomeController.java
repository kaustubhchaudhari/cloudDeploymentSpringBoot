
/*Akhila Kunche, 001251306, kunche.a@husky.neu.edu 
Akshay Nakhawa, 001665873, nakhawa.a@husky.neu.edu 
Kaustubh Chaudhari, 001218494, chaudhari.k@husky.neu.edu 
Prachi Saxena, 001220709, saxena.pr@husky.neu.edu
*/

package com.csye6225.demo.controllers;


import com.csye6225.demo.repositories.UserRepository;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Date;

import com.csye6225.demo.model.User;

@Controller
public class HomeController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String welcome() {

    JsonObject jsonObject = new JsonObject();

    if (SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
      jsonObject.addProperty("message", "you are not logged in!!!");
    } else {
      jsonObject.addProperty("message", "you are logged in. current time is " + new Date().toString());
    }

    return jsonObject.toString();
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String test() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "authorized for /test");
    return jsonObject.toString();
  }

  @RequestMapping(value = "/testPost", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String testPost() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "authorized for /testPost");
    return jsonObject.toString();
  }

  @RequestMapping(path="/user/register", method = RequestMethod.POST, produces = "application/json" , consumes= {"application/json","application/x-www-form-urlencoded"}) // Map ONLY GET Requests
  public @ResponseBody String addNewUser (@RequestBody User person) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    User user = userRepository.findByEmail(person.getEmail());
    if (user != null){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("message", "user email already exists");
      return jsonObject.toString();
    }
    String encrypt = bCryptPasswordEncoder.encode(person.getPass());
    User n = new User();
    n.setPass(encrypt);
    n.setEmail(person.getEmail());
    userRepository.save(n);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "Saved");
    return jsonObject.toString();
  }



}
