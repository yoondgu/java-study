package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.service.OrderService;
import kr.co.bookstore.vo.Order;

public class OrderController {
	
	private static OrderController instance = new OrderController();
	private OrderController() {}
	public static OrderController getInstance() {
		return instance;
	}
	
	private OrderService orderService = OrderService.getInstance();
	
	public void 내구매내역보기(long sessionId) throws SQLException {
		System.out.println("<<  내 구매내역 보기  >>");
		System.out.println("### 구매내역을 확인하세요.");
		
		List<Order> orders = orderService.getMyOrders(sessionId);
		if (orders.isEmpty()) {
			System.out.println("[처리 완료] 사용자의 구매내역이 존재하지 않습니다.");
			return;
		}

		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("주문번호\t주문날짜\t\t주문상태\t주문금액\t구매수량\t결제금액\t적립포인트\t제목");
		System.out.println("--------------------------------------------------------------------------------------");
		for (Order order : orders) {
			System.out.print(order.getNo() + "\t");
			System.out.print(order.getCreatedDate() + "\t");
			System.out.print(order.getStatus() + "\t");
			System.out.print(order.getTotalPrice() + "\t");
			System.out.print(order.getTotalQuantity() + "\t");
			System.out.print(order.getPaymentPrice() + "\t");
			System.out.print(order.getDepositPoint() + "\t");
			System.out.println(order.getTitle());
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}

}
