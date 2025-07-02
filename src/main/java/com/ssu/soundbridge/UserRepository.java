package com.yourdomain.soundbridge.repository;

import com.yourdomain.soundbridge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
} 