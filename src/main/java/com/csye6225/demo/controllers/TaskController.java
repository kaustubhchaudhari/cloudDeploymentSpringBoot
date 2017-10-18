package com.csye6225.demo.controllers;


import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.TaskRepository;
import com.csye6225.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class TaskController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     *
     * Request format :
     * {
     "description" : "task for user53434343"

     }
     * @param task
     * @return
     * @throws NullPointerException
     */
    @RequestMapping(path = "/task/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    // Map ONLY GET Requests
    public @ResponseBody
    String addNewTask(@RequestBody Task task) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = new Task();
                t.setDescription(task.getDescription());
                t.setUser(user);
                taskRepository.save(t);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", "Saved successfully");
                jsonObject.addProperty("ID", t.getTaskid());
                return jsonObject.toString();
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }

    /**
     * request format
     * {
     "taskid" :"5389dfb7-b536-403e-acd3-9b3885a792bd", -- taskID
     "description" : "task for user53434343"

     }
     * @param task
     * @return
     * @throws NullPointerException
     */
    @RequestMapping(path = "/task/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    // Map ONLY GET Requests
    public @ResponseBody
    String updateTask(@RequestBody Task task) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = taskRepository.findByTaskid(task.getTaskid());
                t.setDescription(task.getDescription());
                t.setUser(user);
                taskRepository.save(t);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", "Saved successfully");
                jsonObject.addProperty("ID", t.getTaskid());
                return jsonObject.toString();
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }

}
