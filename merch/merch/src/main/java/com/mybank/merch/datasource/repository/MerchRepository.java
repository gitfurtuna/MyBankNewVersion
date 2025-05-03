package com.mybank.merch.datasource.repository;

import com.mybank.merch.datasource.model.MerchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchRepository extends JpaRepository<MerchEntity,Long> {

    @Query("SELECT m FROM MerchEntity m WHERE m.name = :name")
    Optional<MerchEntity> findByName(@Param("name") String name);
}
