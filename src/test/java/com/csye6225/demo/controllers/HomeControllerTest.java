/**
package com.csye6225.demo.controllers;

import com.csye6225.demo.DemoApplication;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.TaskRepository;
import com.csye6225.demo.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
public class HomeControllerTest {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TaskRepository taskRepository;



    @Test
    public void addNewUser() throws Exception
    {

        User u= new User();
        u.setEmail("k");
        u.setPass("k");
        //if(userRepository==null)
        userRepository.save(u);
        //else
            System.out.println("Done");
         User u2= userRepository.findByEmail("k");
         Assert.assertEquals("k", u2.getEmail());


        /*Task t= new Task();
        t.setDescription("Description for kkkkkkkkkkkkkkkkk");
        t.setUser(u);
        taskRepository.save(t);*/


    }

}
    
    
    
    
    
    **/
