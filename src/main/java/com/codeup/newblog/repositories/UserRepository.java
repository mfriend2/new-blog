package com.codeup.newblog.repositories;

import com.codeup.newblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
