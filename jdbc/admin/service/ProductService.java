package admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import admin.dao.ProductDao;
import admin.vo.Product;

/**
 * 상품관리와 관련된 다양한 서비스를 제공하는 클래스
 * @author doyoung
 *
 */
public class ProductService {

	private ProductDao productDao = new ProductDao();
		
	/**
	 * 신규 상품정보를 전달받아서 새 상품을 등록하는 서비스
	 * @param product 신규 상품정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 예외를 던진다.
	 */
	public void registerProduct(Product product) throws SQLException {
		productDao.insertProduct(product);
	}
	
	/**
	 * 전체 상품정보를 반환하는 서비스
	 * @return 전체 상품정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 예외를 던진다. 
	 */
	public List<Product> getAllProducts() throws SQLException {
		return productDao.findAllProducts();
	}
	
	/**
	 * 상품번호를 전달받아서 상품번호와 일치하는 상품정보를 반환하는 서비스
	 * @param productNo 상품번호
	 * @return 상품정보, 상품번호와 일치하는 상품이 존재하지 않으면 예외를 던진다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 예외를 던진다.
	 */
	public Product getProductDetailInfo(int productNo) throws SQLException {
		Product product = productDao.findProductByNo(productNo);
		if (product == null) {
			throw new IllegalArgumentException("상품번호[" + productNo + "]에 해당하는 상품이 존재하지 않습니다.");
		}
		
		return product;
	}
	
	/**
	 * 검색조건이 저장된 Map객체를 전달받아서 검색조건과 일치하는 상품정보를 반환한다.
	 * @param condition 검색조건이 저장된 Map객체
	 * @return 상품정보 리스트
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 예외를 던진다.
	 */
	public List<Product> searchProducts(Map<String, Object> condition) throws SQLException{
		System.out.println("검색조건: " + condition);
		
		// Map객체 condition은 검색조건이 저장된 객체. (key) : (value)조건내용 / (key) : (value) 검색내용 으로 이루어져 있다.
		// 예를 들어 한 Map 객체 안에 아래 쌍들이 있다.
		//		예시 1) 검색조건 opt(key):"카테고리"(value), keyword(key): "스마트폰"(value), 
		//		예시 2) 검색조건 opt(key):"가격"(value), min(key): 1000(value), max(key): 10000(value)
		String optValue = (String)(condition.get("opt")); // object 객체를 string으로 형변환
		// .toString도 되지만 이건 String 타입에 한해 사용가능한 메소드이니까 형변환으로 작성
		
		List<Product> products = new ArrayList<>();
		if ("카테고리".equals(optValue)) {
			String category = (String)condition.get("keyword");
			products = productDao.findProductsByCategory(category);
			
		} else if ("상품명".equals(optValue)) {
			String name = (String)condition.get("keyword");
			products = productDao.findProductsByName(name);
			
		} else if ("제조회사".equals(optValue)) {
			String company = (String)condition.get("keyword");
			products = productDao.findProductsByCompany(company);
			
		} else if ("가격".equals(optValue)) {
			int minPrice = (Integer)condition.get("min");
			int maxPrice = (Integer)condition.get("max");
			products = productDao.findProductsByPrice(minPrice, maxPrice);
			
		} else if ("판매상태".equals(optValue)) {
			String status = (String)condition.get("status");
			products = productDao.findProductsByStatus(status);
		}
		
		return products;
	}
	
	/**
	 * 상품번호와 할인율을 전달받아서 지정된 상품의 할인가격을 변경하는 서비스
	 * @param productNumbers 상품번호들 예시: [1001, 1034, 1567]
	 * @param discountRate 할인율
	 * @throws SQLException
	 */
	public void discountPrice(Set<Integer> productNumbers, Double discountRate) throws SQLException  {
		
		for (int productNo : productNumbers) {
			Product product = productDao.findProductByNo(productNo);
//			System.out.println("할인가격 변경 전: " + product);
			if (product != null) {	// 번호 중 일부만 null일 경우 중단되지 않도록. 방어적 코딩
				int price = product.getPrice();
				int discountPrice = (int)(price*(1-discountRate));
				product.setDiscountPrice(discountPrice);	// product 객체의 할인가격이 변경되었음
				
				productDao.updateProduct(product);			// db에 변경된 객체의 정보를 전달
			}
//			System.out.println("할인가격 변경 후: " + product);
		}
	}
	
	/**
	 * 상품번호를 전달받아서 지정된 상품들을 무료배송 상품으로 변경하는 서비스
	 * @param productNumbers 상품번호들
	 * @throws SQLException 
	 */
	public void setFreeDeliveryProducts(Set<Integer> productNumbers) throws SQLException {
		for (int productNo : productNumbers) {
			Product product = productDao.findProductByNo(productNo);
			if (product != null) {	// 여러 상품번호 중 일부만 잘못되었을 경우 나머지는 바로 저장할 수 있도록 오류 발생을 회피한 것이다.
				product.setFreeDelivery("Y");
				productDao.updateProduct(product);
			}
		}
	}
	
	/**
	 * 상품번호, 입고량을 전달받아서 지정된 상품의 재고량에 반영하는 서비스 
	 * @param productNumber 상품번호
	 * @param amount 입고량
	 * @throws SQLException
	 */
	public void updateProductStock(int productNumber, int amount) throws SQLException {
		Product product = productDao.findProductByNo(productNumber);
		if (product == null) {
			throw new IllegalArgumentException("상품 번호가 올바르지 않습니다."); // 하나의 상품을 처리하므로 오류 발생시켜 던진다.
		}
		int previousStock = product.getStock();
		product.setStock(previousStock + amount);
		productDao.updateProduct(product);
	}
	
	/**
	 * 전달받은 상품번호에 해당하는 상품들을 폐기상품으로 변경하고, 판매상태를 "절판"으로 변경하는 서비스
	 * @param productNumbers
	 * @throws SQLException
	 */
		// 조회화면에 아예 보이지 않게 하려면 조회 쿼리 where절에 조건을 추가할 것.
	public void deleteProducts(Set<Integer> productNumbers) throws SQLException {
		for (int productNo : productNumbers) {
			Product product = productDao.findProductByNo(productNo);
			if (product != null) {
				product.setStatus("절판");
				product.setDeleted("Y");
				productDao.updateProduct(product);
			}
		}
	}
	
}
