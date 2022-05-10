package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.BookDao;
import kr.co.bookstore.dao.OrderDao;
import kr.co.bookstore.dao.OrderItemDao;
import kr.co.bookstore.util.LoginCheckUtil;
import kr.co.bookstore.vo.Book;
import kr.co.bookstore.vo.LoginedUser;
import kr.co.bookstore.vo.Order;
import kr.co.bookstore.vo.OrderItem;

public class OrderService {

	private static OrderService instance = new OrderService();
	private OrderService() {}
	public static OrderService getInstance() {
		return instance;
	}

	private BookDao bookDao = BookDao.getInstance();
	private OrderDao orderDao = OrderDao.getInstance();
	private OrderItemDao orderItemDao = OrderItemDao.getInstance();
	
	private BookService bookService = BookService.getInstance();
	private UserService userService = UserService.getInstance();
	
	/**
	 * 로그인 일련번호를 전달받아 해당 사용자의 주문정보를 모두 조회한다.
	 * @param sessionId 로그인 일련번호
	 * @return 사용자의 모든 주문정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public List<Order> getMyOrders(long sessionId) throws SQLException {
		LoginedUser loginedUser = LoginCheckUtil.getLoginedUser(sessionId);
		
		return orderDao.getOrdersByUserNo(loginedUser.getUserNo());
	}
	
	/**
	 * 로그인 일련번호, 책번호, 구매수량, 사용포인트를 전달받아 바로 구매하는 기능
	 * @param sessionId 로그인 일련번호
	 * @param bookNo 책번호
	 * @param quantity 구매수량
	 * @param usedPoint 사용 포인트
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void buyBookByNo(int bookNo, int quantity, int usedPoint, long sessionId) throws SQLException {
		LoginedUser loginedUser = LoginCheckUtil.getLoginedUser(sessionId);
		
		// 주문번호 생성하기
		int orderNo = orderDao.generateOrderNo();
		
		// 주문정보의 제목, 총구매가격 등을 계산하기 위해 book 객체 획득
		Book book = bookDao.getBookByNo(bookNo);
		String title = book.getTitle();
		int totalOrderPrice = book.getDiscountPrice()*quantity;
		int paymentPrice = totalOrderPrice - usedPoint;
		int depositPoint = (int)(paymentPrice*0.005);
		int userNo = loginedUser.getUserNo();
		
		// 주문정보를 담은 객체를 만들어서 테이블에 저장
		Order order = new Order();
		order.setNo(orderNo);
		order.setTitle(title);
		order.setTotalPrice(totalOrderPrice);
		order.setUsedPoint(usedPoint);
		order.setPaymentPrice(paymentPrice);
		order.setDepositPoint(depositPoint);
		order.setTotalQuantity(quantity);
		order.setUserNo(userNo);
		orderDao.insertOrder(order);
		
		// 주문상품정보를 담은 객체름 만들어서 테이블에 저장
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderNo(orderNo);
		orderItem.setBookNo(bookNo);
		orderItem.setPrice(book.getDiscountPrice());
		orderItem.setQuantity(quantity);
		orderItemDao.insertOrderItem(orderItem);
		
		// 사용자의 포인트 변경(사용자정보, 포인트 변경이력)
		userService.changeUserPoint(userNo, orderNo, "주문 - 결제", usedPoint*-1);
		userService.changeUserPoint(userNo, orderNo, "주문 - 적립", depositPoint);
		
		// 도서 재고량 변경
		bookService.changeBookStock(bookNo, quantity*-1);
	}
	
}
