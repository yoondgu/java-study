package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.Book;

/**
 * STORE_BOOKS 테이블에 대한 CRUD 기능을 제공하는 클래스다.
 * @author lee_e
 *
 */
public class BookDao {

	private static BookDao instance = new BookDao();
	private BookDao() {}
	public static BookDao getInstance() {
		return instance;
	}
	
	/**
	 * 모든 책 정보를 반환한다.
	 * @return 모든 책정보
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Book> getBooks() throws SQLException {
		String sql = "select * "
				   + "from store_books "
				   + "order by book_no desc";
		
		List<Book> books = new ArrayList<>();
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Book book = new Book();
			book.setNo(rs.getInt("book_no"));
			book.setTitle(rs.getString("book_title"));
			book.setAuthor(rs.getString("book_author"));
			book.setPublisher(rs.getString("book_publisher"));
			book.setDescription(rs.getString("book_description"));
			book.setPrice(rs.getInt("book_price"));
			book.setDiscountPrice(rs.getInt("book_discount_price"));
			book.setOnSell(rs.getString("book_on_sell"));
			book.setStock(rs.getInt("book_stock"));
			book.setCreatedDate(rs.getDate("book_created_date"));
			book.setUpdatedDate(rs.getDate("book_updated_date"));
			
			books.add(book);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return books;
	}
	
	/**
	 * 책번호와 일치하는 책정보를 반환한다.
	 * @param bookNo 책번보
	 * @return 책정보, 책번호가 유효하지 않은 경우 null이 반환될 수 있다.
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public Book getBookByNo(int bookNo) throws SQLException {
		String sql = "select * "
				   + "from store_books "
				   + "where book_no = ? ";
		
		Book book = null;
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, bookNo);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			book = new Book();
			book.setNo(rs.getInt("book_no"));
			book.setTitle(rs.getString("book_title"));
			book.setAuthor(rs.getString("book_author"));
			book.setPublisher(rs.getString("book_publisher"));
			book.setDescription(rs.getString("book_description"));
			book.setPrice(rs.getInt("book_price"));
			book.setDiscountPrice(rs.getInt("book_discount_price"));
			book.setOnSell(rs.getString("book_on_sell"));
			book.setStock(rs.getInt("book_stock"));
			book.setCreatedDate(rs.getDate("book_created_date"));
			book.setUpdatedDate(rs.getDate("book_updated_date"));
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return book;
	}
	
	/**
	 * 수정된 정보가 포함된 책정보를 전달받아서 테이블에 반영시킨다.
	 * @param book 책정보
	 * @throws SQLException 데이터베이스 엑세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void updateBook(Book book) throws SQLException {
		String sql = "update store_books "
				   + "set "
				   + "		book_discount_price = ?, "
				   + "		book_on_sell = ?, "
				   + "		book_stock = ?,"
				   + "		book_updated_date = sysdate "
				   + "where book_no = ?";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, book.getDiscountPrice());
		pstmt.setString(2, book.getOnSell());
		pstmt.setInt(3, book.getStock());
		pstmt.setInt(4, book.getNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();		
	}
}










