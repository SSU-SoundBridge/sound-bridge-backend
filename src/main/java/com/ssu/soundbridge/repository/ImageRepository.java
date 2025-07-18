package com.ssu.soundbridge.repository;

import com.ssu.soundbridge.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
} 