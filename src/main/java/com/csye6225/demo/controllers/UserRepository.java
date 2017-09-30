/*Akhila Kunche, 001251306, kunche.a@husky.neu.edu 
Akshay Nakhawa, 001665873, nakhawa.a@husky.neu.edu 
Kaustubh Chaudhari, 001218494, chaudhari.k@husky.neu.edu 
Prachi Saxena, 001220709, saxena.pr@husky.neu.edu
*/

package com.csye6225.demo.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import com.csye6225.demo.controllers.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends CrudRepository<Person, Long> {

    public List<Person> findByEmail(String email);
}
