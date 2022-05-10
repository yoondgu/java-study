package admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import admin.util.ConnectionUtil;
import admin.vo.Product;

/**
 * SAMPLE_PRODUCTS 테이블에 대한 CRUD 기능을 제공하는 클래스
 * @author doyoung
 *
 */
public class ProductDao {
	
	/**
	 * 신규 상품정보를 전달받아서 새 상품을 SAMPLE_PRODUCTS 테이블에 저장한다.
	 * @param product 상품정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertProduct(Product product) throws SQLException {
		// 입력폼을 만들 때에는 데이터베이스 내 테이블의 제약조건을 꼭 확인하기
		/* NULLABLE인 product_status(default='판매중'), product_free_delivery(default='N'), product_deleted(default='N'), product_updated_date, product_created_date(default=SYSDATE)의 값은 저장하지 않는다.
		 * product_discount_price는 NULLABLE이지만 할인되지 않은 기본 가격이 미리 저장되어 있다.
		 * product_no는 시퀀스의 nextval 함수로 생성한다.
		 */
		String sql = "insert into sample_products "
					+ "(product_no, product_name, product_company, "
					+ "product_price, product_discount_price, product_stock, product_category) "
					+ "values "
					+ "(products_seq.nextval,?,?,?,?,?,?) ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		// 값을 바인딩한다.
		pstmt.setString(1, product.getName());
		pstmt.setString(2, product.getCompany());
		pstmt.setInt(3, product.getPrice());
		pstmt.setInt(4, product.getDiscountPrice());	// 컨트롤러에서 할인가격을 상품가격 값으로 저장해서 전달받았다.
		// 여기서 product.getPrice()로 작성한다면 저장은 원하는 결과대로 되겠지만, product 객체와 테이블 간의 정보 차이가 생긴다. 
		pstmt.setInt(5, product.getStock());
		pstmt.setString(6, product.getCategory());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 변경된 상품정보를 전달받아서 테이블에 적용한다.
	 * 객체 전체를 통째로 주고받으면 일부만 변경하더라도 모두 이 메소드를 쓰면 되고, 어떤 정보가 변경되었는지 확인할 필요가 없다.
	 * @param product 상품정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void updateProduct(Product product) throws SQLException {
		String sql = "update sample_products "
					+ "set "
					+ " product_name = ?, "
					+ " product_company = ?, "
					+ " product_price = ?, "
					+ " product_discount_price = ?, "
					+ " product_stock = ?, "
					+ " product_status = ?, "
					+ " product_deleted = ?, "
					+ " product_free_delivery = ?, "
					+ " product_updated_date = sysdate "	// 시스템의 현재 날짜와 시간정보 반환
					+ "where product_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, product.getName());
		pstmt.setString(2, product.getCompany());
		pstmt.setInt(3, product.getPrice());
		pstmt.setInt(4, product.getDiscountPrice());
		pstmt.setInt(5, product.getStock());
		pstmt.setString(6, product.getStatus());
		pstmt.setString(7, product.getDeleted());
		pstmt.setString(8, product.getFreeDelivery());
		pstmt.setInt(9, product.getNo());
		
		pstmt.executeUpdate();	// execute(sql)을 쓰면 안됨. 0statement 기본 메소드에 sql을 새로 받은 게 되어버림.
		
		pstmt.close();
		connection.close();
	}
	
	// 기본 조회 메소드
	/**
	 * 데이터베이스에 존재하는 모든 상품정보를 반환한다.
	 * @return 상품정보 리스트,  List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findAllProducts() throws SQLException {
		String sql = "select * " // 실제로는 * 말고 속성명 하나씩 다 적어줄 것!!!
				+ "from sample_products "
				+ "order by product_no desc ";
		
		Connection connection = ConnectionUtil.getConnection();
	
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Product> products = resultSetToProducts(rs);
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return products;
	}
				
	// 속성 조건에 따른 상품 조회 메소드
	/**
	 * 상품번호를 전달받아서 상품상세정보를 반환한다.
	 * @param productNo 조회할 상품번호
	 * @return 상품정보, 번호와 일치하는 상품정보가 존재하지 않으면 null을 반환한다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public Product findProductByNo(int productNo) throws SQLException {
		Product product = null;
		String sql = "select * " // 실제로는 * 말고 속성명 하나씩 다 적어줄 것!!!
					+ "from sample_products "
					+ "where product_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, productNo);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			product = resultSetToProduct(rs);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return product;
	}

	
	/**
	 * 전달받은 카테고리명이 포함된 상품정보를 반환한다.
	 * @param category 카테고리명
	 * @return 상품정보 리스트, List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findProductsByCategory(String category) throws SQLException {
		String sql = "select * "
					+ "from sample_products "
					+ "where product_category like '%' || ? || '%' "
					// '%?%'라고 써야 하는데 ?을 사용하기 위해 '%' + ? + '%'로 나누는데, 오라클에서는 문자열 더하기 연산자로 ||를 사용함
					+ "order by product_no desc ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, category);
		ResultSet rs = pstmt.executeQuery();
		List<Product> productList = resultSetToProducts(rs);
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	/**
	 * 전달받은 상품명이 포함된 모든 상품정보를 반환한다.
	 * @param name 상품명
	 * @return 상품정보 리스트, List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findProductsByName(String name) throws SQLException{
		String sql = "select * "
					+ "from sample_products "
					+ "where product_name like '%' || ? || '%' "
					+ "order by product_no desc ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		List<Product> productList = resultSetToProducts(rs);

		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	/**
	 * 전달받은 제조회사명이 포함된 상품정보를 반환한다.
	 * @param company 제조회사명
	 * @return 상품정보 리스트, List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findProductsByCompany(String company) throws SQLException {
		String sql = "select * "
					+ "from sample_products "
					+ "where product_company like '%' || ? || '%' "
					+ "order by product_no desc ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, company);
		ResultSet rs = pstmt.executeQuery();
		List<Product> productList = resultSetToProducts(rs);
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	/**
	 * 전달받은 가격(최소값, 최대값)의 범위에 포함된 상품정보를 반환한다.
	 * @param minPrice 가격 최소값
	 * @param maxPrice 가격 최대값 
	 * @return 상품정보 리스트, List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findProductsByPrice(int minPrice, int maxPrice) throws SQLException {
		String sql = "select * "
					+ "from sample_products "
					+ "where product_price between ? and ? "
					+ "order by product_price asc ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, minPrice);
		pstmt.setInt(2, maxPrice);
		ResultSet rs = pstmt.executeQuery();
		List<Product> productList = resultSetToProducts(rs);
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}

	/**
	 * 전달받은 판매상태가 일치하는 상품정보를 반환한다.
	 * @param company 제조회사명
	 * @return 상품정보 리스트, List&lt;Product&gt;는 절대 null이 아니다.
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Product> findProductsByStatus(String status) throws SQLException {
		String sql = "select * "
					+ "from sample_products "
					+ "where product_status = ? "
					+ "order by product_no desc ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, status);
		ResultSet rs = pstmt.executeQuery();
		List<Product> productList = resultSetToProducts(rs);
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}

	// 조회 관련 중복 코드에 대한 메소드
	/**
	 * ResultSet에 포함된 모든 행의 데이터를 가져와서 Product 목록으로 반환한다.
	 * @param rs ResultSet
	 * @return 상품정보 리스트
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	private List<Product> resultSetToProducts(ResultSet rs) throws SQLException {
		List<Product> products = new ArrayList<>();
		
		while (rs.next()) {
			Product product = resultSetToProduct(rs);
			products.add(product);
		}
		
		return products;
	}
	
	/**
	 * ResultSet 객체에서 현재 커서가 위치한 행의 데이터를 가져와서 Product객체에 저장하고 반환한다.
	 * @param rs ResultSet
	 * @return 현재 커서가 위치한 행의 값을 저장하고 있는 Product 객체
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	private Product resultSetToProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setNo(rs.getInt("product_no"));
		product.setCategory(rs.getString("product_category"));
		product.setName(rs.getString("product_name"));
		product.setCompany(rs.getString("product_company"));
		product.setPrice(rs.getInt("product_price"));
		product.setDiscountPrice(rs.getInt("product_discount_price"));
		product.setStock(rs.getInt("product_stock"));
		product.setStatus(rs.getString("product_status"));
		product.setCreatedDate(rs.getDate("product_created_date"));
		product.setFreeDelivery(rs.getString("product_free_delivery"));
		product.setDeleted(rs.getString("product_deleted"));
		product.setUpdatedDate(rs.getDate("product_updated_date"));
		
		return product;
	}

}
