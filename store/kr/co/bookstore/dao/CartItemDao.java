package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.dto.CartItemDto;
import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.CartItem;

/**
 * STORE_CART_ITEMS 테이블에 대한 CRUD 기능을 제공하는 클래스다.
 * @author lee_e
 *
 */
public class CartItemDao {

	private static CartItemDao instance = new CartItemDao();
	private CartItemDao() {}
	public static CartItemDao getInstance() {
		return instance;
	}
	
	/**
	 * 장바구니아이템정보를 전달받아서 테이블에 저장시킨다.
	 * @param cartItem 새 장바구니 아이템정보
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into store_cart_items (cart_item_no, user_no, book_no, cart_item_quantity) "
				   + "values(store_cartitems_seq.nextval, ?, ?, ?) ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, cartItem.getUserNo());
		pstmt.setInt(2, cartItem.getBookNo());
		pstmt.setInt(3, cartItem.getQuantity());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
		
	}
	
	/**
	 * 사용자번호를 전달받아서 일치하는 사용자번호의 모든 장바구니 아이템정보를 반환한다.
	 * @param userNo 사용자번호
	 * @return 장바구니 아이템 목록
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<CartItemDto> getCartItemsByUserNo(int userNo) throws SQLException {
		String sql = "select C.cart_item_no, C.book_no, B.book_title, B.book_price, B.book_discount_price, "
				   + "       C.cart_item_quantity, B.book_discount_price * C.cart_item_quantity as order_price "
				   + "from store_cart_items C, store_books B "
				   + "where C.book_no = B.book_no "
				   + "and C.user_no = ? "
				   + "order by C.cart_item_no desc ";
		
		List<CartItemDto> cartItems = new ArrayList<>();
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			CartItemDto dto = new CartItemDto();
			dto.setItemNo(rs.getInt("cart_item_no"));
			dto.setBookNo(rs.getInt("book_no"));
			dto.setTitle(rs.getString("book_title"));
			dto.setPrice(rs.getInt("book_price"));
			dto.setDiscountPrice(rs.getInt("book_discount_price"));
			dto.setQuantity(rs.getInt("cart_item_quantity"));
			dto.setOrderPrice(rs.getInt("order_price"));
			
			cartItems.add(dto);
		}
		rs.close();
		pstmt.close();
		connection.close();
		
		return cartItems;
	}
	
	/**
	 * 사용자번호를 전달받아서 일치하는 사용자번호의 모든 장바구니 아이템을 삭제한다.
	 * @param userNo 사용자번호
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void deleteAllCartItemsByUserNo(int userNo) throws SQLException {
		String sql = "delete from store_cart_items "
				   + "where user_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 장바구니 아이템번호를 전달받아서 일치하는 장바구니 번호의 장바구니 아이템을 삭제한다.
	 * @param itemNo 장바구니 아이템번호
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void deleteCartItemByItemNo(int itemNo) throws SQLException {
		String sql = "delete from store_cart_items "
				   + "where cart_item_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, itemNo);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
}








