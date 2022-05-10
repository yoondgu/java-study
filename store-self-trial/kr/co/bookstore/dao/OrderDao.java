package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.Order;
import kr.co.bookstore.vo.OrderItem;

public class OrderDao {

	private static OrderDao instance = new OrderDao();
	private OrderDao() {}
	public static OrderDao getInstance() {
		return instance;
	}
	
	public void insertOrderItem(OrderItem orderItem) throws SQLException{
		String sql = "insert into store_order_items(order_item_no, order_no, book_no, order_item_price, order_item_quantity) "
					+ "values(store_orderitems_seq.nextval, ?, ?, ?, ?) ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, orderItem.getOrderNo());
		pstmt.setInt(2, orderItem.getBookNo());
		pstmt.setInt(3, orderItem.getPrice());
		pstmt.setInt(4, orderItem.getQuantity());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public void insertOrder(Order order) throws SQLException {
		String sql = "insert into store_orders(order_no, order_title, order_total_price, "
					+ "order_used_point, order_payment_price, order_deposit_point, order_total_quantity, user_no) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?) ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, order.getNo());
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
					+ "where user_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		
		ResultSet rs = pstmt.executeQuery();
		List<Order> orders = new ArrayList<>();
		
		while (rs.next()) {
			Order order = new Order();
			order.setNo(rs.getLong("order_no"));
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
		
		return orders;
	}
	
	
}
