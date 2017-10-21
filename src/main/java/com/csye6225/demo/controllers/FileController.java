package com.csye6225.demo.controllers;

import com.csye6225.demo.Storage.StorageProperties;
import com.csye6225.demo.Storage.StorageService;
import com.csye6225.demo.model.FileAttachment;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.FileRepository;
import com.csye6225.demo.repositories.TaskRepository;
import com.csye6225.demo.repositories.UserRepository;
import com.google.gson.JsonObject;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PostRemove;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FileRepository fileRepository;

    //HttpServletResponse response;

    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    private StorageProperties stoprop;
    public String loc;

    @RequestMapping(path = "/file/attach", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String attachFile(@RequestParam("file") MultipartFile file, @RequestParam String taskid, HttpServletRequest request, HttpServletResponse response) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = taskRepository.findByTaskid(taskid);
                if (null != t) {
                    if(t.getUser()==user)
                    {
                        FileAttachment fa = new FileAttachment();
                        loc=  "/home/prachi/Documents/"+file.getOriginalFilename();
                        System.out.println("Path: "+ loc);
                        storageService.store(file);
                        fa.setLocation(loc);
                        fa.setTask(t);
                        fileRepository.save(fa);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Saved successfully");
                        return jsonObject.toString();
                    }

                    else
                    {
                        try
                        {
                            System.out.println("Wrong Credentials");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
                else
                {
                    System.out.println("Outside 4");
                }
            }
            else
            {
                System.out.println("Outside 3");
            }

        }
        else
        {
            System.out.println("Outside 2");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "unauthorized user");
        return jsonObject.toString();
    }

    @Transactional
    @PostMapping("/file/delete")
    @ResponseBody
    public String del(@RequestParam String filename, HttpServletRequest request, HttpServletResponse response) throws InvalidDataAccessApiUsageException {

        StorageProperties base = new StorageProperties();
        String part1 = base.getLocation();
        String complete = part1 + "/" + filename;
        File ob = new File(complete);
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());

            if (ob.exists()) {
                FileAttachment fob = fileRepository.findByLocation(complete);
                Task t = taskRepository.findByTaskid(fob.getTask().getTaskid());
                if (null != t) {
                    if (t.getUser() == user) {
                        ob.delete();
                        System.out.println("Fileid: ");
                        System.out.println(fob.getFileId());
                        //taskRepository.deleteTaskByFileAttachmentsAndTaskid(fob, t.getTaskid());
                        fileRepository.deleteByFileId(fob.getFileId());
                        System.out.println("Successfully deleted");
                        return "redirect:/";
                    }
                } else
                {
                    try
                    {
                        System.out.println("Wrong Credentials");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return "";
    }

    @RequestMapping(path = "/tasks/{taskid}/attachments", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String showFiles(@PathVariable("taskid") String taskid, HttpServletRequest request, HttpServletResponse response) throws NullPointerException, IOException, ServletException {
        System.out.println("Inside");
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            System.out.println("Username: " + user.getEmail());
            if (null != user) {
                Task t = taskRepository.findByTaskid(taskid);
                if (null != t) {
                    if(t.getUser()==user)
                    {
                        List<FileAttachment> files=fileRepository.findByTask(t);
                        for (FileAttachment file:files)
                        {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty(file.getFileId(), file.getLocation());
                            System.out.println(jsonObject.toString());
                        }

                    }

                    else
                    {
                        try
                        {
                            System.out.println("Wrong Credentials");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
                else
                {
                    System.out.println("Outside 4");
                }
            }
            else
            {
                System.out.println("Outside 3");
            }

        }
        else
        {
            System.out.println("Outside 2");
        }
        return"";
    }
}
