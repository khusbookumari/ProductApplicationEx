package com.kk.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.error.ProductNotFoundException;
import com.kk.model.Product;
import com.kk.model.ProductVo;
import com.kk.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	
	@Autowired
	private ProductRepository repo;
	
	@Override
	public Integer saveProduct(Product p) {
		Product p1 = repo.save(p);
		logger.info("Saved Product {}",p1.getId());
		return p1.getId();
	}
	public Integer saveProduct(ProductVo p) {

		Double dis = p.getProdPrice()*.20;
		Double gst = p.getProdPrice()*.18;
		Product save = repo.save(new Product(p.getProdName(), p.getProdPrice(), p.getProdVendor(), gst , dis , p.getProdNote()));
		logger.info("product '"+save.getId()+"' saved successfully");
		return save.getId();
	}


	public String deleteProduct(Integer id) {
		Optional<Product> prod = repo.findById(id);
		if(!prod.isPresent()) {
			logger.warn("product id '"+id+"' not available");
			return null;
		}
		repo.deleteById(id);
		logger.info("prodcut id '"+id+"' deleted");
		return "Deleted id :"+id;
	}


	public Product getOneProduct(Integer id) throws ProductNotFoundException {
		return repo.findById(id).orElseThrow(()->
		{			
			logger.warn("product id '"+id+"' not available");
			throw new ProductNotFoundException("product id '"+id+"' not available");
		});
	}


	public List<Product> getAllProducts() {
		logger.info("Find All Product API called");
		List<Product> list = repo.findAll();
		if(list.isEmpty())
			logger.warn("No Product Available");
		return list;
	}
	
	public String findProductByName(String name) {
		Optional<Product> opt = repo.findByProdName(name);
		if(!opt.isPresent()) {
			logger.warn("Product Name "+name+" Not Available");
			return "Product Name "+name+" Not Available";
		}
		Product product = opt.get();
		try {
			return new ObjectMapper().writeValueAsString(product);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("error ",e);
		}
		return null;
	}
}
