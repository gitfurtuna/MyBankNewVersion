package com.mybank.authservice_my_bank.datasource.repository;

import com.mybank.authservice_my_bank.datasource.model.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
}
