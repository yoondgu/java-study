package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.BookDao;
import kr.co.bookstore.vo.Book;

/**
 * 책정보 관련 서비스를 제공하는 클래스다.
 * @author lee_e
 *
 */
public class BookService {

	private static BookService instance = new BookService();
	private BookService() {}
	public static BookService getInstance() {
		return instance;
	}
	
	private BookDao bookDao = BookDao.getInstance();
	
	/**
	 * 등록된 모든 책정보를 반환하는 서비스를 제공한다.
	 * @return 모든 책정보
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Book> getAllBooks() throws SQLException {
		return bookDao.getBooks();
	}
	
	/**
	 * 지정된 책번호화 일치하는 책정보를 반환하는 서비스를 제공한다.
	 * @param bookNo 책번호
	 * @return 책정보
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public Book getBookInfo(int bookNo) throws SQLException {
		return bookDao.getBookByNo(bookNo);
	}
	
	/**
	 * 책번호와 변경수량을 전달받아서 책의 재고량을 변경하는 서비스를 제공안다.
	 * @param bookNo 책번호
	 * @param amount 변경수량(음수값도 가능하다.)
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
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
