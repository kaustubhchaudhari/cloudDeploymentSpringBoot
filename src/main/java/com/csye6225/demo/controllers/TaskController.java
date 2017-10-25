package com.csye6225.demo.controllers;


import com.csye6225.demo.model.FileAttachment;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.FileRepository;
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
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;


@Controller
public class TaskController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FileRepository fileRepository;


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
    @RequestMapping(path = "/task/delete", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    // Map ONLY GET Requests
    public @ResponseBody
    String deleteTask(@RequestBody Task task) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            try {
                if (null != user) {

                    Task t = taskRepository.findByTaskid(task.getTaskid());
                    if (t.getUser() == user) {
                        List<FileAttachment> list = t.getFileAttachments();
                        for (FileAttachment fa : list) {
                            File ob = new File(fa.getLocation());
                            if (ob.exists()) {
                                ob.delete();
                                System.out.println("Fileid: ");
                                System.out.println(fa.getFileId());
                                //fileRepository.delete(fa);
                            }
                        }
                        taskRepository.delete(t);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Deleted successfully");
                        jsonObject.addProperty("ID", t.getTaskid());
                        return jsonObject.toString();
                    }
                }
            } catch (Exception e) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", e.getMessage());
                return jsonObject.toString();
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }

}
