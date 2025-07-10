package com.ssu.soundbridge.dto;

import java.util.List;

public class StoreDto {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String category;
    private List<String> images; // url 리스트
    private String kakaoMapId;
    private Long id;

    public StoreDto() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    public String getKakaoMapId() { return kakaoMapId; }
    public void setKakaoMapId(String kakaoMapId) { this.kakaoMapId = kakaoMapId; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
} 