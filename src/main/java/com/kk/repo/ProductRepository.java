package com.kk.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kk.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	public Optional<Product> findByProdName(String name);
}
