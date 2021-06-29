package com.kk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Data
@Entity
@Table(name = "tbl_product")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prodid")
	private Integer id;
	@NonNull
	@Column(name = "product_name" ,  unique = true)
	private String prodName;

	@NonNull
	@Column(name = "product_price")
	private Double prodPrice;
	
	@NonNull
	@Column(name = "product_vendor")
	private String prodVendor;
	
	@NonNull
	@Column(name = "product_gst")
	private Double prodGst;
	
	@NonNull
	@Column(name = "product_discount")
	private Double prodDis;
	
	@NonNull
	@Column(name = "product_notes")
	private String prodNote;
}
