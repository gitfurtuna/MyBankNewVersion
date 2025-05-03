package com.mybank.merch.datasource.repository;

import com.mybank.merch.datasource.model.ShoppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity,Long> {


}
