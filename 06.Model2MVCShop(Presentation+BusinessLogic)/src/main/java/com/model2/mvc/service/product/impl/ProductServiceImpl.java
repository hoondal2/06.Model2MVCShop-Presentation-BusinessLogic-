package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserDao;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Service("productServiceImpl")

public class ProductServiceImpl implements ProductService{
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public Product addProduct(Product product) throws Exception {
		productDao.addProduct(product);
		return product;
	}

	public Product getProduct(int prodNo) throws Exception{ 
		return productDao.getProduct(prodNo);
	}
	
	public Product getProduct(String prodName) throws Exception{ 
		return productDao.getProduct(prodName);
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception{ 
		List<Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	public void updateProduct(Product product) throws Exception{ // 상품 정보 수정
		productDao.updateProduct(product);
	}

}