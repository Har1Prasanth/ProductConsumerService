package com.example.MessageService.Repo;

import com.example.MessageService.DTO.ProductEntity;
import com.example.MessageService.Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByProductName(String productName);

    ProductEntity findByProductNameAndPrice(String productName, long price);
}
