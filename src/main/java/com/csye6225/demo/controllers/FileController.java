package com.csye6225.demo.controllers;

import com.csye6225.demo.model.FileAttachment;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.FileRepository;
import com.csye6225.demo.repositories.TaskRepository;
import com.csye6225.demo.repositories.UserRepository;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FileRepository fileRepository;

    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Request format :
     * {
     *     taskID :
     *     file
     * }
     * @param
     * @return
     * @throws NullPointerException
     */
    @RequestMapping(path = "/file/attach", method = RequestMethod.POST, produces = "application/json")
    // Map ONLY GET Requests
    public @ResponseBody
    String attachFile(@RequestParam("file") MultipartFile file, @RequestParam String taskid) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = taskRepository.findByTaskid(taskid);
                if (null != t) {
                    // TO-DO : Store file in filesystem

                    FileAttachment fa = new FileAttachment();
                    fa.setLocation("");
                    fa.setTask(t);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Saved successfully");
                    jsonObject.addProperty("ID", fa.getFileId());
                    return jsonObject.toString();
                }
            }
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }
}
