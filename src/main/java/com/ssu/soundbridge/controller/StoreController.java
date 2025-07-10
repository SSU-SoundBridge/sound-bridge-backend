package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.domain.Store;
import com.ssu.soundbridge.service.StoreService;
import com.ssu.soundbridge.dto.StoreDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreDto> getAllStores() {
        return storeService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StoreDto getStore(@PathVariable Long id) {
        Store store = storeService.findById(id);
        return toDto(store);
    }

    @PostMapping
    public StoreDto createStore(@RequestBody Store store) {
        return toDto(storeService.save(store));
    }

    @PutMapping("/{id}")
    public StoreDto updateStore(@PathVariable Long id, @RequestBody Store store) {
        store.setId(id);
        return toDto(storeService.save(store));
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable Long id) {
        storeService.delete(id);
    }

    private StoreDto toDto(Store store) {
        if (store == null) return null;
        StoreDto dto = new StoreDto();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setDescription(store.getDescription());
        dto.setLatitude(store.getLatitude());
        dto.setLongitude(store.getLongitude());
        dto.setCategory(store.getCategory() != null ? store.getCategory().getName() : null);
        if (store.getImages() != null) {
            java.util.List<String> imageUrls = store.getImages().stream().map(img -> img.getUrl()).toList();
            dto.setImages(imageUrls);
        } else {
            dto.setImages(null);
        }
        dto.setKakaoMapId(store.getKakaoMapId());
        return dto;
    }
} 