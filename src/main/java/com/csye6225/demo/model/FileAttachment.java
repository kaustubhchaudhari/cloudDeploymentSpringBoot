package com.csye6225.demo.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="file")
public class FileAttachment
{
    @Id
    @Column(name="fileId")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name ="uuid",
            strategy = "uuid2"
    )
    private String fileId;
    //private FileAttachment fileAttachment;
    public String getFileId()
    {
        return fileId;
    }
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }


    @ManyToOne
    @JoinColumn(name = "taskid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;
    public Task getTask()
    {
        return task;
    }
    public void setTask(Task task)
    {
        this.task = task;
    }




    @Column(name="location", length=4096)
    private String location;
    public String getLocation()
    {
        return location;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    private String originalName;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
