/*Akhila Kunche, 001251306, kunche.a@husky.neu.edu 
Akshay Nakhawa, 001665873, nakhawa.a@husky.neu.edu 
Kaustubh Chaudhari, 001218494, chaudhari.k@husky.neu.edu 
Prachi Saxena, 001220709, saxena.pr@husky.neu.edu
*/

package com.csye6225.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name ="uuid",
            strategy = "uuid2"
    )
    private String id;

    private String pass;

    private String email;

    @OneToMany(mappedBy = "user", orphanRemoval = true )
    private List<Task> taskList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String name) {
        this.pass = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
