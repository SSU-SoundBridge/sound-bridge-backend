package com.ssu.soundbridge.dto;

import java.util.List;
import java.util.Optional;

public class StoreRequestDto {
    private Optional<String> name = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<Double> latitude = Optional.empty();
    private Optional<Double> longitude = Optional.empty();
    private Optional<String> category = Optional.empty();
    private Optional<List<String>> images = Optional.empty();
    private Optional<String> kakaoMapId = Optional.empty();

    public StoreRequestDto() {}
    public Optional<String> getName() { return name; }
    public void setName(Optional<String> name) { this.name = name; }
    public Optional<String> getDescription() { return description; }
    public void setDescription(Optional<String> description) { this.description = description; }
    public Optional<Double> getLatitude() { return latitude; }
    public void setLatitude(Optional<Double> latitude) { this.latitude = latitude; }
    public Optional<Double> getLongitude() { return longitude; }
    public void setLongitude(Optional<Double> longitude) { this.longitude = longitude; }
    public Optional<String> getCategory() { return category; }
    public void setCategory(Optional<String> category) { this.category = category; }
    public Optional<List<String>> getImages() { return images; }
    public void setImages(Optional<List<String>> images) { this.images = images; }
    public Optional<String> getKakaoMapId() { return kakaoMapId; }
    public void setKakaoMapId(Optional<String> kakaoMapId) { this.kakaoMapId = kakaoMapId; }
} 