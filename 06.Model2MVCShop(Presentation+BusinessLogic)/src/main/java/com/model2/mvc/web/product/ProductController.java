package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

//==> 상품관리 Controller
@Controller
public class ProductController {

	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService; 
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping("/addProductView.do") // 단순 네비게이션
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "forward:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product, @RequestParam("prodName") String prodName, Model model ) throws Exception {

		System.out.println("/addProduct.do");
		
		//Business Logic
		productService.addProduct(product);
		product = productService.getProduct(prodName);
		
		model.addAttribute("vo", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo, Model model ) throws Exception {
		
			System.out.println("/getProduct.do");
		
			Product product = productService.getProduct(prodNo);
			model.addAttribute("vo", product);
	
		// Model 과 View 연결
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}	
		
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product, Model model) throws Exception{

		
		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		
//		product = productService.getProduct(prodNo);
//		model.addAttribute("vo", product);
//		System.out.println("전달 받은 prodNo의 값?????????????????????????"+prodNo);

		return "redirect:/getProduct.do?menu=manage&prodNo="+product.getProdNo();
		// forward로 하면 prodNo이 스트링배열로 바뀌어 전달됨, NumberFormat 에러 -> ?
	}
	
	@RequestMapping("/listProduct.do")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	

	
}
