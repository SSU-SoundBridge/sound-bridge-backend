package com.ssu.soundbridge.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // getter, setter, 생성자
    public Image() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
} 