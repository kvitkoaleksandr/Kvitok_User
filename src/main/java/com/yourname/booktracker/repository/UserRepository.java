package com.yourname.booktracker.repository;

import com.yourname.booktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}