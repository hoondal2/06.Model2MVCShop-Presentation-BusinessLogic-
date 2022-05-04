package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductService {
	
	// 상품 등록
	public Product addProduct(Product product) throws Exception; 
		
	// 상품정보 번호로 조회
	public Product getProduct(int prodNo) throws Exception; 
	public Product getProduct(String prodName) throws Exception; 
	
	//상품목록 조회
	public Map<String, Object> getProductList(Search searchVO) throws Exception; 
	
	// 상품 정보 수정
	public void updateProduct(Product product) throws Exception; 
		
}