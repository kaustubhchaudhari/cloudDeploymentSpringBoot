package com.csye6225.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileAttachment {
    @Id
    @Column(name = "fileid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid2"
    )
    private String fileId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskid")
    private User task;


    @Column(name = "filepath", length = 4096)

    private String filePath;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getTask() {
        return task;
    }

    public void setTask(User task) {
        this.task = task;
    }
}
