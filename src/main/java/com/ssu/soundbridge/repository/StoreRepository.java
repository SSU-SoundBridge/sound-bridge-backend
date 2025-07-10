package com.ssu.soundbridge.repository;

import com.ssu.soundbridge.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT *, (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(latitude)))) AS distance FROM store ORDER BY distance ASC", nativeQuery = true)
    List<Store> findAllOrderByDistance(@Param("lat") double latitude, @Param("lng") double longitude);
} 