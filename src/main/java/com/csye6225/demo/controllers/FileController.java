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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;

@Controller
public class FileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FileRepository fileRepository;

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
    String attachFile(@RequestParam("file") MultipartFile file, @RequestParam String taskid) throws NullPointerException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                Task t = taskRepository.findByTaskid(taskid);
                if (null != t) {
                    if(t.getUser()==user)
                    {
                        String fileName = t.getTaskid() + "_" +t.getFileAttachments().size() + 1;
                        FileAttachment fa = new FileAttachment();
                        loc=  "/home/andy/Documents/"+fileName;

                        System.out.println("Path: "+ loc);
                        storageService.store(file, fileName);
                        fa.setLocation(loc);
                        fa.setTask(t);
                        fa.setOriginalName(file.getOriginalFilename());
                        fileRepository.save(fa);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("message", "Saved successfully");
                        jsonObject.addProperty("FileId", fa.getFileId());
                        return jsonObject.toString();
                    }

                    else
                    {
                        System.out.println("Wrong Credentials");
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
    public String del(@RequestParam String fileId) throws InvalidDataAccessApiUsageException {



        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(ud.getUsername());
            if (null != user) {
                FileAttachment fa = fileRepository.findByFileId(fileId);
                if (null != fa) {
                    File ob = new File(fa.getLocation());
                    if (ob.exists()) {
                        //FileAttachment fob = fileRepository.findByLocation(complete);
                        Task t = taskRepository.findByTaskid(fa.getTask().getTaskid());
                        if (null != t) {
                            if (t.getUser() == user) {
                                ob.delete();
                                System.out.println("Fileid: ");
                                System.out.println(fa.getFileId());
                                //taskRepository.deleteTaskByFileAttachmentsAndTaskid(fob, t.getTaskid());
                                //fileRepository.deleteByFileIdAndAndTask(fob.getFileId(), t);
                                fileRepository.delete(fa);
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("message","File Delted Succesfully");
                                return jsonObject.toString();
                            }
                        }
                    }
                }
            }
        }JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message","Could not delete  ");
        return jsonObject.toString();

    }
}
