package com.ssu.soundbridge.service;

import com.ssu.soundbridge.domain.Image;
import com.ssu.soundbridge.repository.ImageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
} 