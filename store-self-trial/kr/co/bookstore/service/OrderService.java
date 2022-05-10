package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.dao.BookDao;
import kr.co.bookstore.dao.CartItemDao;
import kr.co.bookstore.dao.LoginedUserDao;
import kr.co.bookstore.dao.OrderDao;
import kr.co.bookstore.dto.CartItemDto;
import kr.co.bookstore.vo.Book;
import kr.co.bookstore.vo.LoginedUser;
import kr.co.bookstore.vo.Order;
import kr.co.bookstore.vo.OrderItem;
import kr.co.bookstore.vo.User;

public class OrderService {

	private static OrderService instance = new OrderService();
	private OrderService() {}
	public static OrderService getInstance() {
		return instance;
	}
	
	private BookDao bookDao = BookDao.getInstance();
	private LoginedUserDao loginedUserDao = LoginedUserDao.getInstance();
	private OrderDao orderDao = OrderDao.getInstance();
	private CartItemDao cartItemDao = CartItemDao.getInstance();
	
	private UserService userService = UserService.getInstance();
	private BookService bookService = BookService.getInstance();
	
	/**
	 * 로그인 일련번호와 책번호, 사용포인트를 전달받아서 번호가 일치하는 책을 바로 구매한다.
	 * @param bookNo 책번호
	 * @param sessionId 로그인 일련번호
	 * @param usePoint 사용 포인트, 총 금액에서 사용 포인트 만큼을 차감한다.
	 * @throws SQLException
	 */
	public void orderOneBook(int bookNo, long sessionId, int usePoint, int quantity) throws SQLException {		
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		Book book = bookDao.getBookByNo(bookNo);
		if (book == null) {
			throw new RuntimeException("책 정보가 존재하지 않습니다.");
		}
		
		User user = userService.getUserInfo(sessionId);
		int currentPoint = user.getPoint();
		if (usePoint > currentPoint) {
			System.out.println("[입력 오류] 포인트가 부족합니다.");
			return;
		}
		
		// store_order의 PK, store_orders_item의 FK인 orderNo를 유닉스타임으로 부여한다.
		long orderNo = System.currentTimeMillis();
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderNo(orderNo);
		orderItem.setBookNo(bookNo);
		orderItem.setPrice(book.getDiscountPrice());
		orderItem.setQuantity(quantity); 
		
		Order order = new Order();
		order.setNo(orderNo);
		order.setTitle(book.getTitle());
		order.setTotalPrice(orderItem.getPrice()*orderItem.getQuantity());
		order.setUsedPoint(usePoint);
		order.setPaymentPrice(order.getTotalPrice() - usePoint);
		order.setDepositPoint((int)(order.getPaymentPrice()*0.01));
		order.setTotalQuantity(orderItem.getQuantity());
		order.setUserNo(loginedUser.getUserNo());
		
		orderDao.insertOrder(order);	// STORE_ORDERS에서 정보를 먼저 저장해야 STORE_ORDER_ITEMS에서 FK 참조할 수 있다.
		orderDao.insertOrderItem(orderItem);
		
		bookService.changeBookStock(bookNo, - orderItem.getQuantity());
		userService.changePoint(order);
	}
	
	/**
	 * 로그인 일련번호를 전달받아서 해당 사용자의 모든 장바구니 아이템을 구매한다.
	 * @param sessionId 로그인 일련번호
	 * @throws SQLException
	 */
	public void orderAllCartItems(long sessionId, int usePoint) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		int currentPoint = userService.getUserInfo(sessionId).getPoint();
		if (usePoint > currentPoint) {
			System.out.println("[입력 오류] 포인트가 부족합니다.");
			return;
		}
		
		long orderNo = System.currentTimeMillis(); // 사용자 정보가 인증되면 주문정보일련번호를 생성한다.
		// 카트아이템정보를 이용하여 주문상품정보 객체들을 만들어 리스트에 담는다. 
		List<CartItemDto> cartItemDtos = cartItemDao.getAllCartItemsByUserNo(loginedUser.getUserNo());
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItemDto dto : cartItemDtos) {
			Book book = bookDao.getBookByNo(dto.getBookNo());
			if (book != null) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo(orderNo);
				orderItem.setBookNo(book.getNo());
				orderItem.setPrice(book.getDiscountPrice());
				orderItem.setQuantity(dto.getQuantity());
				orderItems.add(orderItem);
			}
		}
		
		// 주문상품정보들을 종합한 주문정보를 저장하기 위한 주문정보 객체를 만든다.
		// 주문상품정보를 이용해 주문타이틀, 총금액, 총수량을 계산하여 저장한다. (추후 포인트 반영 필요)
		Book firstBook = bookDao.getBookByNo(orderItems.get(0).getBookNo());
		String title = firstBook.getTitle();
		if (orderItems.size() > 1) {
			title = firstBook.getTitle() + " 외 " + (orderItems.size()-1) + "권";
		}
		
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getPrice()*orderItem.getQuantity();
		}
		
		int totalQuantity = 0;
		for (OrderItem orderItem : orderItems) {
			totalQuantity += orderItem.getQuantity();
		}
		
		Order order = new Order();
		order.setNo(orderNo);
		order.setTitle(title);
		order.setTotalPrice(totalPrice);	
		order.setUsedPoint(usePoint);
		order.setPaymentPrice(totalPrice - usePoint);
		order.setDepositPoint((int)(order.getPaymentPrice()*0.01));
		order.setTotalQuantity(totalQuantity);
		order.setUserNo(loginedUser.getUserNo());
		
		// 주문정보를 먼저 데이터베이스에 저장하고, 주문정보일련번호를 참조하는 복수의 주문상품정보를 데이터베이스에 저장한다.
		orderDao.insertOrder(order);
		for (OrderItem orderItem : orderItems) {
			orderDao.insertOrderItem(orderItem);
		}
		
		for (OrderItem orderItem : orderItems) {
			bookService.changeBookStock(orderItem.getBookNo(), - orderItem.getQuantity());
		}
		userService.changePoint(order);
		
	}
	
	/**
	 * 로그인 일련번호를 전달받아 해당 사용자의 모든 주문정보를 조회한다.
	 * @param sessionId
	 * @return 사용자의 주문정보 리스트 객체
	 * @throws SQLException
	 */
	public List<Order> getMyOrders(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그아웃 상태입니다.");
		}
		
		return orderDao.getOrdersByUserNo(loginedUser.getUserNo());
	}
	
	
}
