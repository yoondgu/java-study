package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.BookDao;
import kr.co.bookstore.vo.Book;

/**
 * 책 정보 관련 서비스를 제공하는 클래스
 * @return
 */
public class BookService {

	private static BookService instance = new BookService();
	private BookService() {}
	public static BookService getInstance() {
		return instance;
	}
	
	private BookDao bookDao = BookDao.getInstance();
	
	/**
	 * 등록된 모든 책 정보를 반환하는 서비스를 제공한다.
	 * @return 모든 책 정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Book> getAllBooks() throws SQLException {
		return bookDao.getBooks(); 
	}
	
	/**
	 * 지정된 책 번호와 일치하는 책 정보를 반환하는 서비스를 제공한다.
	 * @param bookNo
	 * @return 책 정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public Book getBookInfo(int bookNo) throws SQLException {
		return bookDao.getBookByNo(bookNo);
	}
	
	/**
	 * 책번호와 변경수량을 전달받아서 책의 재고량을 변경하는 서비스를 제공한다.
	 * @param bookNo 책번호
	 * @param amount 변경수량 (음수값도 가능하다.)
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	// 사용자 서비스에서 update메소드는 구매로 인한 재고 변경할 때에만 필요하다.
	public void changeBookStock(int bookNo, int amount) throws SQLException {
		Book book = bookDao.getBookByNo(bookNo);
		if (book == null) {
			throw new RuntimeException("책정보가 존재하지 않습니다.");
		}
		
		book.setStock(book.getStock() + amount);
		if (book.getStock() == 0) {
			book.setOnSell("N");
		}
		
		bookDao.updateBook(book);
	}
}
