package com.scm.repo;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
//extra method
//custom query methods
//custom finder methos
 Optional<User> findByEmail(String email);
 Optional<User> findByEmailAndPassword(String email, String password);

 Optional<User> findByEmailToken(String emailToken);
}
