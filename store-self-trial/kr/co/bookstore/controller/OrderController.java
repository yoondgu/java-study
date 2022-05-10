package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.service.OrderService;
import kr.co.bookstore.service.UserService;
import kr.co.bookstore.util.KeyboardReader;
import kr.co.bookstore.vo.Order;
import kr.co.bookstore.vo.User;

public class OrderController {
	
	private static OrderController instance = new OrderController();
	private OrderController() {}
	public static OrderController getInstance() {
		return instance;
	}

	private OrderService orderService = OrderService.getInstance();
	private UserService userService = UserService.getInstance();
	
	private KeyboardReader keyboardReader = KeyboardReader.getInstance();
	
	public void 바로구매하기(int bookNo, long sessionId) throws SQLException {
		System.out.println("<<  바로구매  >>");
		System.out.println("### 조회중인 도서를 바로 구매합니다.");
		
		System.out.print("수량 입력: ");
		int quantity = keyboardReader.readInt();
		
		User user = userService.getUserInfo(sessionId);
		System.out.println("[현재 보유 포인트 금액: " + user.getPoint() + "원]");
		System.out.print("사용할 포인트 금액 입력: ");
		int usePoint = keyboardReader.readInt();
		
		orderService.orderOneBook(bookNo, sessionId, usePoint, quantity);
		
		System.out.println("[처리 완료] 주문이 완료되었습니다.");
	}
	
	public void 주문내역보기(long sessionId) throws SQLException {
		System.out.println("<<  주문내역 보기  >>");
		System.out.println("### 주문내역을 조회합니다.");
		
		List<Order> orders = orderService.getMyOrders(sessionId);
		if (orders.isEmpty()) {
			System.out.println("[처리 완료] 로그인된 사용자의 주문내역이 존재하지 않습니다.");
			return;
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
	    System.out.println("주문번호\t\t주문상태\t총금액\t사용포인트\t결제금액\t적립포인트\t제목");
	    System.out.println("--------------------------------------------------------------------------------------");
	    for (Order order : orders) {
	    	System.out.print(order.getNo() + "\t");
	    	System.out.print(order.getStatus() + "\t");
	    	System.out.print(order.getTotalPrice() + "\t");
	    	System.out.print(order.getUsedPoint() + "\t\t");
	    	System.out.print(order.getPaymentPrice() + "\t");
	    	System.out.print(order.getDepositPoint() + "\t");
	    	System.out.println(order.getTitle());
	    }
	    System.out.println("--------------------------------------------------------------------------------------");
	    
	}

	
}
