package com.floriano.login_system_backend_maven.repositories;

import com.floriano.login_system_backend_maven.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer > {

    UserDetails findByEmail(String email);
}
