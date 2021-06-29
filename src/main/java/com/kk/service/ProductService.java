package com.kk.service;

import java.util.List;

import com.kk.model.Product;
import com.kk.model.ProductVo;

public interface ProductService {
		Integer saveProduct(ProductVo p);
		Integer saveProduct(Product p);
		//void updateProduct(ProductVo p);
		String deleteProduct(Integer id);
		Product getOneProduct(Integer id);
		List<Product> getAllProducts();
		String findProductByName(String name);
}
