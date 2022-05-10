package admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import admin.service.ProductService;
import admin.util.StringUtils;
import admin.vo.Product;
import jdbc.sample.KeyboardReader;

public class ProductAdminController {
	
	private KeyboardReader keyboard = new KeyboardReader();
	private ProductService productService = new ProductService();

	public void menu() {
		try {
			System.out.println("[상품관리 관리자 프로그램]");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("1.전체조회  2.검색  3.상세조회  4.신규등록  5.할인  6.무료배송  7.입고  8.폐기  -1.종료");
			System.out.println("------------------------------------------------------------------------------------");
			
			System.out.print("메뉴번호 입력: ");
			int menuNo = keyboard.readInt();
			System.out.println();
			
			if (menuNo == 1) {
				전체상품조회();
			} else if (menuNo == 2) {
				상품검색();
			} else if (menuNo == 3) {
				상품상세조회();
			} else if (menuNo == 4) {
				신규등록();
			} else if (menuNo == 5) {
				할인상품지정();
			} else if (menuNo == 6) {
				무료배송지정();
			} else if (menuNo == 7) {
				상품입고처리();
			} else if (menuNo == 8) {
				상품폐기처리();
			} else if (menuNo == -1) {
				종료();
			}
			
		} catch (Exception e) {
			System.out.println("[오류 발생] " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		menu();
	}
	
	private void 전체상품조회() throws SQLException {
		System.out.println("<< 전체 상품 목록 >>");
		System.out.println("### 전체 상품 목록을 확인하세요.");
		
		List<Product> products = productService.getAllProducts();
		
	      if (products.isEmpty()) {
	    	  System.out.println("[처리 완료] 조회 가능한 상품이 없습니다.");
	      } else {
	    	  상품리스트출력(products);
	    	  System.out.println("[처리 완료] 상품 전체 목록이 출력되었습니다.");
	      }
		System.out.println();
		System.out.println();
	}
	
	private void 상품검색() throws SQLException {
	      System.out.println("<< 상품 검색 >>");
	      System.out.println("------------------------------------------------------------------------------------");
	      System.out.println("1.카테고리  2.이름  3.제조사  4.가격  5.상태  -1.종료");
	      System.out.println("------------------------------------------------------------------------------------");
	      
	      System.out.print("검색메뉴 입력: ");
	      int menuNo = keyboard.readInt();
	      

	      Map<String, Object> condition = new HashMap<>();
	      if (menuNo == 1) {
	         System.out.println("<< 카테고리로 상품 검색 >>");
	         System.out.println("### 카테고리를 입력해서 상품을 검색해보세요");
	         
	         System.out.print("카테고리 입력: ");
	         String category = keyboard.readString();
	         
	         condition.put("opt", "카테고리");
	         condition.put("keyword", category);
	         
	      } else if (menuNo == 2) {
	         System.out.println("<< 상품이름으로 상품 검색 >>");
	         System.out.println("### 상품이름을 입력해서 상품을 검색해보세요");
	         
	         System.out.print("상품이름 입력: ");
	         String name = keyboard.readString();
	         
	         condition.put("opt", "상품명");
	         condition.put("keyword", name);
	         
	      } else if (menuNo == 3) {
	         System.out.println("<< 제조회사로 상품 검색 >>");
	         System.out.println("### 제조회사명을 입력해서  상품을 검색해보세요");
	         
	         System.out.print("제조회사이름 입력: ");
	         String company = keyboard.readString();
	         
	         condition.put("opt", "제조회사");
	         condition.put("keyword",company);
	         
	      } else if (menuNo == 4) {
	         System.out.println("<< 가격으로 상품 검색 >>");
	         System.out.println("### 가격을 입력해서  상품을 검색해보세요");
	         
	         System.out.print("최소가격 입력: ");
	         int minPrice = keyboard.readInt();
	         System.out.print("최대가격 입력: ");
	         int maxPrice = keyboard.readInt();
	         
	         condition.put("opt", "가격");
	         condition.put("min", minPrice);
	         condition.put("max", maxPrice);
	         
	      } else if (menuNo == 5) {
	         System.out.println("<< 판매상태로 상품 검색 >>");
	         System.out.println("### 판매상태를 선택해서 상품을 검색해보세요");
	         
	         System.out.print("판매상태(1.판매중, 2.재고없음, 3.입고예정, 4.절판): ");
	         int statusNumber = keyboard.readInt();
	         
	         condition.put("opt", "판매상태");
	         if (statusNumber == 1) {
	        	 condition.put("status", "판매중");	// 상태값은 다른 옵션과 달리 정확히 같은 문자열이어야 한다.
	         } else if (statusNumber == 2) {
	        	 condition.put("status", "재고없음");
	         } else if (statusNumber == 3) {
	        	 condition.put("status", "입고예정");
	         } else if (statusNumber == 4) {
	        	 condition.put("status", "절판");
	         }
	         
	      } else if (menuNo == -1) {
	         System.out.println("### 상품검색을 종료합니다.");
	         return;
	      }
	      
	      // 검색옵션과 요청값을 담은 Map 객체를 전달해 다양한 타입의 값을 통해 조회하는 메소드를 실행한다.
	      List<Product> products = productService.searchProducts(condition);
	      if (products.isEmpty()) {
	    	  System.out.println("[처리 완료] 검색 조건에 해당하는 상품이 없습니다.");
	      } else {
	    	  상품리스트출력(products);
	    	  System.out.println("[처리 완료] 상품 전체 목록이 출력되었습니다.");
	      }
	      System.out.println();
	      System.out.println();
	      
	      상품검색();
	   }
	
	private void 상품상세조회() throws SQLException {
		System.out.println("<< 상품 상세 조회 >>");
		System.out.println("### 상품번호를 입력해서 상품상세정보를 조회해보세요.");
		
		System.out.print("상품번호 입력: ");
		int productNo = keyboard.readInt();
		
		Product product = productService.getProductDetailInfo(productNo);
		
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("상품번호: " + product.getNo());
		System.out.println("카테고리: " + product.getCategory());
		System.out.println("상품이름: " + product.getName());
		System.out.println("제조회사: " + product.getCompany());
		System.out.println("상품가격: " + StringUtils.numberToCurrency(product.getPrice()) + "원");
		System.out.println("할인가격: " + StringUtils.numberToCurrency(product.getDiscountPrice()) + "원");
		System.out.println("재고수량: " + product.getStock());
		System.out.println("판매상태: " + product.getStatus());
		System.out.println("무료배송: " + product.getFreeDelivery());
		System.out.println("폐기여부: " + product.getDeleted());
		System.out.println("수정날짜: " + StringUtils.nullToBlank(product.getUpdatedDate()));
		System.out.println("등록날짜: " + product.getCreatedDate());
		System.out.println("-------------------------------------------------------------------------");
		//오류 처리로 중단 안되게 하면 안되나?
	}
	
	private void 신규등록() throws SQLException {
		System.out.println("<< 상품 신규 등록 >>");
		System.out.println("### 카테고리, 이름, 제조사, 가격, 입고량을 입력하세요.");
		
		System.out.print("카테고리 입력: ");
		String category = keyboard.readString();
		System.out.print("상품명 입력: ");
		String name = keyboard.readString();
		System.out.print("제조사 입력: ");
		String company = keyboard.readString();
		System.out.print("가격 입력: ");
		int price = keyboard.readInt();
		System.out.print("입고량 입력: ");
		int stock = keyboard.readInt();
		
		Product product = new Product();
		product.setCategory(category);
		product.setName(name);
		product.setCompany(company);
		product.setPrice(price);
		product.setDiscountPrice(price); // 할인가격은 입력받지 않지만 신규상품 등록인 경우 할인가격은 상품가격과 같은 값을 저장한다.
		product.setStock(stock);
		
		productService.registerProduct(product);
		
		System.out.println("[처리 완료] 상품이 등록되었습니다.");
		
	}
	
	private void 할인상품지정() throws SQLException {
		System.out.println("<< 할인 상품 지정 >>");
		System.out.println("### 할인을 적용할 상품번호를 ,로 구분해서 입력하고, 할인율을 입력하세요");
		
		System.out.print("할인을 적용할 상품번호 입력:");
		String productNumberText = keyboard.readString();
		System.out.print("할인율 입력: "); 	// 10%
		String discountRateText = keyboard.readString();
		
		Set<Integer> productNumbers = StringUtils.textToNumbers(productNumberText);
		/*
		 * discountRateText = "10%"
		 * discountRateText.replace("%", "") => "10"
		 * Double.parseDouble(discountRateText.replace("%", "")) => 10.0
		 * Double.parseDouble(discountRateText.replace("%", ""))/100 => 0.1
		 */
		Double discountRate = Double.parseDouble(discountRateText.replace("%", ""))/100;
		
		productService.discountPrice(productNumbers, discountRate);
		
		System.out.println("[처리 완료] 지정된 상품의 할인가격이 변경되었습니다.");
		
	}
	
	private void 무료배송지정() throws SQLException {
		System.out.println("<< 무료 배송 지정 >>");
		System.out.println("### 무료배송으로 지정할 상품번호를 ,로 구분해서 입력하세요");
		
		System.out.print("무료배송으로 지정할 상품번호 입력: ");
		String productNumberText = keyboard.readString();
		Set<Integer> productNumbers = StringUtils.textToNumbers(productNumberText);
		productService.setFreeDeliveryProducts(productNumbers);
		
		System.out.println("[처리 완료] 지정된 상품이 무료배송으로 변경되었습니다.");
	}
	
	private void 상품입고처리() throws SQLException {
		System.out.println("<< 상품 입고 처리 >>");
		System.out.println("### 입고된 상품번호와 입고량을 입력하세요.");
		
		System.out.print("상품번호 입력: ");
		int productNumber = keyboard.readInt();
		System.out.print("입고량 입력: ");
		int amount = keyboard.readInt();
		
		productService.updateProductStock(productNumber, amount);
		
		System.out.println("[처리 완료] 지정된 상품의 입고량이 재고에 반영되었습니다.");
	}
	
	private void 상품폐기처리() throws SQLException {
		System.out.println("<< 상품 폐기 처리 >>");
		System.out.println("### 폐기할 상품번호를 ,로 구분해서 입력하세요.");
		
		System.out.print("상품번호 입력: ");
		String productNumberText = keyboard.readString();
		Set<Integer> productNumbers = StringUtils.textToNumbers(productNumberText);
		productService.deleteProducts(productNumbers);
		
		System.out.println("[처리 완료] 지정된 상품이 폐기되었습니다.");

	}
	
	private void 종료() throws SQLException {
		System.out.println("<< 프로그램 종료 >>");
		System.exit(0);
	}
	
	private void 상품리스트출력(List<Product> products) {
  	  System.out.println("-------------------------------------------------------------------------");
  	  System.out.println("상품번호\t상품가격\t판매상태\t제조회사\t상품명");
  	  System.out.println("-------------------------------------------------------------------------");
  	  for (Product product : products) {
  		  System.out.print(product.getNo() + "\t");
  		  System.out.print(product.getPrice() + "\t");
  		  System.out.print(product.getStatus() + "\t");
  		  System.out.print(product.getCompany() + "\t");
  		  System.out.println(product.getName());
  	  }
  	  System.out.println("-------------------------------------------------------------------------");

	}
	

	
}
