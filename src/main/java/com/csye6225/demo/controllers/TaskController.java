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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class TaskController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(path = "/task/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody String addNewTask(@RequestBody Task task, HttpServletRequest request, HttpServletResponse response) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = new Task();
                if(t.getDescription().length()<=4096)
                {
                t.setDescription(task.getDescription());
                t.setUser(user);
                taskRepository.save(t);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", "Saved successfully");
                jsonObject.addProperty("ID", t.getTaskid());
                return jsonObject.toString();
                }
                else
                {
                    try
                    {
                        System.out.println("File content longer than allowed");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }

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

    @RequestMapping(path = "/tasks", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String showTasks() throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("Entered");
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            System.out.println("Tasks of user with user email "+user.getEmail());
            if (null != user) {
                List<Task> tasks= taskRepository.findByUser(user);
                for (Task t:tasks)
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(t.getTaskid(), t.getDescription());
                    System.out.println(jsonObject.toString());
                }
            }
        }
        else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "unauthorized user");
            return jsonObject.toString();
        }
        return "";
    }

}
