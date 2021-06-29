package com.kk.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.error.ProductNotFoundException;
import com.kk.model.Product;
import com.kk.model.ProductVo;
import com.kk.service.ProductService;

/*
 * this class is used for manage the product details
 */
@Controller
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService service;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/*
	 * Use for find all Product from DB
	 */
	@GetMapping("/")
	public String homePage(Model model) {
		List<Product> list = service.getAllProducts();
		list.forEach(System.out::println);
		logger.debug("list of Product {}",list);
		model.addAttribute("list", list);
		return "Home";
	}


	@GetMapping("/reg")
	public String regPage() {
		return "ProdReg";
	}

	/*
	 * Insert the product in DB
	 */
	@PostMapping("/save")
	public String saveprod(@ModelAttribute ProductVo req,Model model)
	{
		logger.info("/save {}",gson.toJson(req));
		Integer id = service.saveProduct(req);
		model.addAttribute("msg", "Product id "+id + " saved in DB");
		return "redirect:/";
	}
	/*
	 * Update the Product Detais
	 */
	@PostMapping("/update/save")
	public String updateOrSaveProd(@ModelAttribute Product req,Model model)
	{
		logger.info("/update/save : {} " ,gson.toJson(req));
		Integer id = service.saveProduct(req);
		model.addAttribute("msg", "Product id "+id + " Updated in DB");
		return "redirect:/";
	}

	@GetMapping("/update/{id}")
	public String updateProd(@PathVariable Integer id ,Model model)
	{
		logger.info("/update/ : id {}",id);
		Product optProd = null;
		try {
			optProd = service.getOneProduct(id);
			model.addAttribute("product", optProd );
			return "EditProduct";
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("pnf", "product id '"+id+"' not available, so Unale to Update");
			return "Error";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteProd(@PathVariable Integer id , Model model)
	{
		logger.info("/delete/ id : {}",id);
		String msg = service.deleteProduct(id);
		if(msg == null || msg.isEmpty()) {
			logger.info("product id '{}' not available so Unable to delete",id);
			model.addAttribute("pnf", "product id '"+id+"' not available so Unable to delete");
			return "Error";
		}
		model.addAttribute("msg", msg);
		return "redirect:/";
	}
	@GetMapping("/find/{name}")
	public String findProdByName(@PathVariable String name , Model model)
	{
		logger.info("/find/ name : {}",name);
		String msg = service.findProductByName(name);
		model.addAttribute("msg", msg);
		return "FindPage";
	}

}