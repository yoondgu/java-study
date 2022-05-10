package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.OrderItem;

/**
 * STORE_ORDER_ITEMS 테이블에 대한 CRUD 작업을 제공하는 클래스
 * @author doyoung
 *
 */
public class OrderItemDao {

	private static OrderItemDao instance = new OrderItemDao();
	private OrderItemDao() {}
	public static OrderItemDao getInstance() {
		return instance;
	}
	
	/**
	 * 주문상품정보를 전달받아서 테이블에 저장한다.
	 * @param orderItem 주문상품 정보
	 * @throws SQLException SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertOrderItem(OrderItem orderItem) throws SQLException {
		String sql = "insert into store_order_items(order_item_no, order_no, book_no, order_item_price, order_item_quantity) "
					+ "values(store_orderitems_seq.nextval, ?, ?, ?, ?)";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, orderItem.getOrderNo());
		pstmt.setInt(2, orderItem.getBookNo());
		pstmt.setInt(3, orderItem.getPrice());
		pstmt.setInt(4, orderItem.getQuantity());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	
}
