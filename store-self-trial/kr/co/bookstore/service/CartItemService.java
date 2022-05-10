package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.BookDao;
import kr.co.bookstore.dao.CartItemDao;
import kr.co.bookstore.dao.LoginedUserDao;
import kr.co.bookstore.dto.CartItemDto;
import kr.co.bookstore.vo.Book;
import kr.co.bookstore.vo.CartItem;
import kr.co.bookstore.vo.LoginedUser;

/**
 * 장바구니 관련 서비스를 제공하는 클래스
 * @author doyoung
 *
 */
public class CartItemService {

	private static CartItemService instance = new CartItemService();
	private CartItemService() {}
	public static CartItemService getInstance() {
		return instance;
	}
	
	private BookDao bookDao = BookDao.getInstance();
	private CartItemDao cartItemDao = CartItemDao.getInstance();
	private LoginedUserDao loginedUserDao = LoginedUserDao.getInstance();
	
	/**
	 * 책번호와 로그인 일련번호를 전달받아서 로그인한 사용자의 장바구니 아이템으로 저장하는 서비스를 제공한다.
	 * @param bookNo 책 정보
	 * @param sessionId 로그인 일련번호
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 발생시킨다.
	 */
	public void addBookIntoCart(int bookNo, long sessionId) throws SQLException {
		Book book = bookDao.getBookByNo(bookNo);
		if (book == null) {
			throw new RuntimeException("책 정보가 존재하지 않습니다.");
		}
		
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		CartItem cartItem = new CartItem();
		// cartItem의 no는 시퀀스로 저장된다.
		cartItem.setBookNo(bookNo);
		cartItem.setUserNo(loginedUser.getUserNo());
		cartItem.setQuantity(1);
		
		cartItemDao.insertCartItem(cartItem);
	}
	
	/**
	 * 로그인 일련번호를 전달받아서 로그인된 사용자의 모든 장바구니 아이템정보를 반환하는 서비스를 제공한다.
	 * @param sessionId 로그인 일련번호
	 * @return 로그인된 사용자의 모든 장바구니 아이템정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 발생시킨다.
	 */
	public List<CartItemDto> getMyCartItems(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		return cartItemDao.getAllCartItemsByUserNo(loginedUser.getUserNo());
	}
	
	/**
	 * 로그인 일련번호를 전달받아서 로그인된 사용자의 모든 장바구니 아이템정보를 삭제하는 서비스를 제공한다.
	 * @param sessionId 로그인 일련번호
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 발생시킨다.
	 */
	public void deleteAllMyCartItems(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		cartItemDao.deleteAllCartItemsByUserNo(loginedUser.getUserNo());
	}
	
	/**
	 * 장바구니 아이템번호를 전달받아서 번호가 일치하는 모든 장바구니 아이템정보를 삭제하는 서비스를 제공한다.
	 * @param itemNo 로그인 일련번호
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 발생시킨다.
	 */
	public void deleteCartItemsByItemNo(int itemNo, long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		cartItemDao.deleteCartItemByItemNo(itemNo);
	}
	
}
