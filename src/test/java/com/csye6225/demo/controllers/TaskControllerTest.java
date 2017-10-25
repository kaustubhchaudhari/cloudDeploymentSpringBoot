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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
public class TaskControllerTest {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void git () throws Exception
    {
        /*Task t= new Task();
        User use= new User();
        use.setEmail("l");
        use.setPass("l");
        //userRepository.save(use);
        t.setDescription("Description for lllllllllllllllllll");
        t.setUser(use);
        taskRepository.save(t);
        //Assert.assertEquals(t, taskRepository.findByUser(u));
        //assertArrayEquals(t,taskRepository.findByUser(u));
        List<Task> ob= taskRepository.findByUser(use);
        for (Task tob: ob)
        {
            String des= tob.getDescription();
            Assert.assertEquals("Description for lllllllllllllllllll", des);
        }*/

    }

}