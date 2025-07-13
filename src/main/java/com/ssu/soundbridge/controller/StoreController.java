package com.ssu.soundbridge.controller;

import com.ssu.soundbridge.domain.Store;
import com.ssu.soundbridge.service.StoreService;
import com.ssu.soundbridge.dto.StoreDto;
import com.ssu.soundbridge.dto.StoreRequestDto;
import com.ssu.soundbridge.domain.Category;
import com.ssu.soundbridge.domain.Image;
import com.ssu.soundbridge.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    private final CategoryService categoryService;

    public StoreController(StoreService storeService, CategoryService categoryService) {
        this.storeService = storeService;
        this.categoryService = categoryService;
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

    @GetMapping("/nearby")
    public List<StoreDto> getStoresByDistance(@RequestParam double latitude, @RequestParam double longitude) {
        return storeService.findAllOrderByDistance(latitude, longitude).stream().map(this::toDto).toList();
    }

    @PostMapping
    public StoreDto createStore(@RequestBody StoreRequestDto request) {
        // 필수값 체크
        if (!request.getName().isPresent() || !request.getLatitude().isPresent() || !request.getLongitude().isPresent() || !request.getCategory().isPresent()) {
            throw new IllegalArgumentException("name, latitude, longitude, category는 필수입니다.");
        }
        Store store = new Store();
        store.setName(request.getName().orElse(null));
        store.setDescription(request.getDescription().orElse(null));
        store.setLatitude(request.getLatitude().orElse(null));
        store.setLongitude(request.getLongitude().orElse(null));
        store.setKakaoMapId(request.getKakaoMapId().orElse(null));
        // 카테고리 이름으로 연결/생성
        String categoryName = request.getCategory().orElse(null);
        Category category = categoryService.findAll().stream()
            .filter(c -> c.getName().equals(categoryName))
            .findFirst()
            .orElseGet(() -> categoryService.save(new Category(categoryName)));
        store.setCategory(category);
        // 이미지 url 리스트로 연결
        if (request.getImages().isPresent()) {
            java.util.List<Image> newImages = request.getImages().get().stream().map(url -> {
                Image img = new Image();
                img.setUrl(url);
                img.setStore(store);
                return img;
            }).collect(java.util.stream.Collectors.toList());
            store.setImages(new java.util.ArrayList<>(newImages));
        }
        return toDto(storeService.save(store));
    }

    @PutMapping("/{id}")
    public StoreDto updateStore(@PathVariable Long id, @RequestBody StoreRequestDto request) {
        Store store = storeService.findById(id);
        if (store == null) return null;
        if (request.getName() != null) store.setName(request.getName().orElse(null));
        if (request.getDescription() != null) store.setDescription(request.getDescription().orElse(null));
        if (request.getLatitude() != null) store.setLatitude(request.getLatitude().orElse(null));
        if (request.getLongitude() != null) store.setLongitude(request.getLongitude().orElse(null));
        if (request.getKakaoMapId() != null) store.setKakaoMapId(request.getKakaoMapId().orElse(null));
        if (request.getCategory() != null) {
            String categoryName = request.getCategory().orElse(null);
            Category category = categoryService.findAll().stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElseGet(() -> categoryService.save(new Category(categoryName)));
            store.setCategory(category);
        }
        if (request.getImages() != null) {
            java.util.List<Image> newImages = request.getImages().get().stream().map(url -> {
                Image img = new Image();
                img.setUrl(url);
                img.setStore(store);
                return img;
            }).collect(java.util.stream.Collectors.toList());
            if (store.getImages() == null) {
                store.setImages(new java.util.ArrayList<>());
            } else {
                store.getImages().clear();
            }
            store.getImages().addAll(newImages);
        }
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