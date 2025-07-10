package com.ssu.soundbridge.service;

import com.ssu.soundbridge.domain.Store;
import com.ssu.soundbridge.repository.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    public Store save(Store store) {
        return storeRepository.save(store);
    }

    public void delete(Long id) {
        storeRepository.deleteById(id);
    }

    public List<Store> findAllOrderByDistance(double latitude, double longitude) {
        return storeRepository.findAllOrderByDistance(latitude, longitude);
    }
} 