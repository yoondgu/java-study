package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.Order;

/**
 * STORE_ORDERS 테이블에 대한 CRUD 기능을 제공하는 클래스
 * @author doyoung
 *
 */
public class OrderDao {

	private static OrderDao instance = new OrderDao();
	private OrderDao() {}
	public static OrderDao getInstance() {
		return instance;
	}
	
	/**
	 * STORE_ORDERS_SEQ 시퀀스에서 새로운 일련번호를 발급받아서 반환한다.
	 * @return 새 주문번호
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public int generateOrderNo() throws SQLException {
		int orderNo = 0;
		
		String sql = "select store_orders_seq.nextval as order_no "
					+ "from dual";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();	// 위의 SQL 실행결과는 무조건 결과행이 하나 존재하기 때문에 rs.next()의 결과가 true인지 if문으로 확인할 필요 없다.
		orderNo = rs.getInt("order_no");
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return orderNo;
	}
	
	/**
	 * 주문정보를 전달받아서 테이블에 저장한다.
	 * @param order 주문정보
	 * @throws SQLException  데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertOrder(Order order) throws SQLException {
		String sql = "insert into store_orders(order_no, order_title, order_total_price, order_used_point, "
				+ "order_payment_price, order_deposit_point, order_total_quantity, user_no) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		// order_no에 store_orders_seq.nextval을 직접 쓰는 대신 외부에서 생성한 주문번호를 넣는 이유
		// 주문상품, 포인트변경이력은 주문번호를 FK로 갖는다. 
		// 주문정보를 저장할 때 시퀀스에서 일련번호를 획득해서 바로 저장하면 그 주문번호를 주문상품저장, 포인트변경이력 저장에서 사용할 수 없다.
		// 따라서 주문번호를 외부에서 미리 획득하고, 획득된 주문번호를 각각의 vo객체에 저장해서 DAO에 전달 후 저장시키면
		// 주문정보, 주문상품정보, 포인트 변경이력정보가 모두 같은 주문번호를 가지게 된다.
		// 즉, 특정 주문에 대해서 주문상품정보, 포인트 변경이력정보가 같은 주문번호로 관계가 형성된다.
		// (.currentval은 connection을 닫으면 쓸 수 없기 때문에 이 상황에서 활용 불가하다.)
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, order.getNo());
		pstmt.setString(2, order.getTitle());
		pstmt.setInt(3, order.getTotalPrice());
		pstmt.setInt(4, order.getUsedPoint());
		pstmt.setInt(5, order.getPaymentPrice());
		pstmt.setInt(6, order.getDepositPoint());
		pstmt.setInt(7, order.getTotalQuantity());
		pstmt.setInt(8, order.getUserNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public List<Order> getOrdersByUserNo(int userNo) throws SQLException {
		String sql = "select * from store_orders "
					+ "where user_no = ? "
					+ "order by order_no desc";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		
		ResultSet rs = pstmt.executeQuery();
		List<Order> orders = new ArrayList<>();
		while (rs.next()) {
			Order order = new Order();
			order.setNo(rs.getInt("order_no"));
			order.setStatus(rs.getString("order_status"));
			order.setTitle(rs.getString("order_title"));
			order.setTotalPrice(rs.getInt("order_total_price"));
			order.setUsedPoint(rs.getInt("order_used_point"));
			order.setPaymentPrice(rs.getInt("order_payment_price"));
			order.setDepositPoint(rs.getInt("order_deposit_point"));
			order.setTotalQuantity(rs.getInt("order_total_quantity"));
			order.setUserNo(rs.getInt("user_no"));
			order.setCreatedDate(rs.getDate("order_created_date"));
			order.setUpdatedDate(rs.getDate("order_updated_date"));
			orders.add(order);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return orders;
	}
	
	
}
