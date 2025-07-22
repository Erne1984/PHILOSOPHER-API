package com.floriano.login_system_backend_maven.repositories.UserRepository;

import com.floriano.login_system_backend_maven.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer > {

    UserDetails findByEmail(String email);
}
