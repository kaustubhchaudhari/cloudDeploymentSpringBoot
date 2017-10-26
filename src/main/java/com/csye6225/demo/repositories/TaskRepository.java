package com.csye6225.demo.repositories;

import com.csye6225.demo.model.FileAttachment;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;



public interface TaskRepository extends CrudRepository<Task, Long>
{

    public List<Task> findByUser(User user);

    public Task findByTaskid(String id);

    public void deleteTaskByFileAttachments(FileAttachment fileAttachment);

    public void deleteTaskByFileAttachmentsAndTaskid(FileAttachment fileAttachment, String taskId);

}

