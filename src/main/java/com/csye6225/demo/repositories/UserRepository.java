/*Akhila Kunche, 001251306, kunche.a@husky.neu.edu 
Akshay Nakhawa, 001665873, nakhawa.a@husky.neu.edu 
Kaustubh Chaudhari, 001218494, chaudhari.k@husky.neu.edu 
Prachi Saxena, 001220709, saxena.pr@husky.neu.edu
*/

package com.csye6225.demo.repositories;

//import org.springframework.data.repository.CrudRepository;
import com.csye6225.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);
}
