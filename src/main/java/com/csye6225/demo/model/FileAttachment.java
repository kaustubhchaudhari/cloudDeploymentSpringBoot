package com.csye6225.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="file")
public class FileAttachment {
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Id
    @Column(name="fileId")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name ="uuid",
            strategy = "uuid2"
    )
    private String fileId;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task")
    private Task task;



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name="location", length=4096)

    private String location;
}
