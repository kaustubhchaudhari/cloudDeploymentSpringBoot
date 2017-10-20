package com.csye6225.demo.repositories;

import com.csye6225.demo.model.FileAttachment;
import com.csye6225.demo.model.Task;
import com.csye6225.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<FileAttachment, Long> {

    public List<FileAttachment> findByTask(Task task);

    public FileAttachment findByFileId(String fileId);

    public FileAttachment findByLocation(String location);

    public void deleteByFileIdAndAndTask(String fileId, Task task);
}
