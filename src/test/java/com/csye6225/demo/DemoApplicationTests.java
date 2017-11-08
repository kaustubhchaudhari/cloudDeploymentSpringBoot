/**package com.csye6225.demo;

import com.csye6225.demo.repositories.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  @Autowired
  private UserRepository userRepository;


  @Ignore
  @Test
  public void contextLoads() {
    assertEquals("b", userRepository.findByEmail("b"));
  }

}
*/
