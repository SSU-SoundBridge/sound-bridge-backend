package com.ssu.soundbridge.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Store> stores;

    // getter, setter, 생성자
    public Category() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public java.util.List<Store> getStores() { return stores; }
    public void setStores(java.util.List<Store> stores) { this.stores = stores; }
} 