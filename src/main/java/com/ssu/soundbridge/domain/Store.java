package com.ssu.soundbridge.domain;

import jakarta.persistence.*;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Image> images;

    @Column(unique = true)
    private String kakaoMapId;

    // getter, setter, 생성자

    public Store() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public java.util.List<Image> getImages() { return images; }
    public void setImages(java.util.List<Image> images) { this.images = images; }
    public String getKakaoMapId() { return kakaoMapId; }
    public void setKakaoMapId(String kakaoMapId) { this.kakaoMapId = kakaoMapId; }
} 