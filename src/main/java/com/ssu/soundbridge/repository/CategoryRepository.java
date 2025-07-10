package com.ssu.soundbridge.repository;

import com.ssu.soundbridge.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
} 