package com.floriano.philosophy_api.repositories.UserRepository;

import com.floriano.philosophy_api.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer > {

    UserDetails findByEmail(String email);
}
