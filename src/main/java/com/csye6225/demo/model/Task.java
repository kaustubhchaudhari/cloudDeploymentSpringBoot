package com.csye6225.demo.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="task")
public class Task
{
    @Id
    @Column(name="taskid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name ="uuid",
            strategy = "uuid2"
    )
    private String taskid;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;


    @Column(name="description", length=4096)

    private String description;

    public List<FileAttachment> getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(List<FileAttachment> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }

    @OneToMany(mappedBy = "task", orphanRemoval = true,cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<FileAttachment> fileAttachments;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
