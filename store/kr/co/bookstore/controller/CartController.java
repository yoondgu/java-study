package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dto.CartItemDto;
import kr.co.bookstore.service.CartItemService;
import kr.co.bookstore.util.KeyboardReader;

public class CartController {

	private static CartController instance = new CartController();
	private CartController() {}
	public static CartController getInstance() {
		return instance;
	}
	
	KeyboardReader keyboard = KeyboardReader.getInstance();
	
	private CartItemService cartItemService = CartItemService.getInstance();
	
	public void 내장바구니보기(long sessionId) throws SQLException {
		System.out.println("<< 내 장바구니 보기 >>");
		System.out.println("### 장바구니에 저장된 아이템정보를 확인해보세요.");
		
		List<CartItemDto> cartItems = cartItemService.getMyCartItems(sessionId);
		if (cartItems.isEmpty()) {
			System.out.println("### 장바구니에 저장된 아이템이 없습니다.");
			return;
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("아이템번호\t\t가격\t할인가격\t수량\t구매가격\t\t제목");
		System.out.println("--------------------------------------------------------------------------------------");
		for (CartItemDto dto : cartItems) {
			System.out.print(dto.getItemNo() + "\t\t");
			System.out.print(dto.getPrice() + "\t");
			System.out.print(dto.getDiscountPrice() + "\t");
			System.out.print(dto.getQuantity() + "\t");
			System.out.print(dto.getOrderPrice() + "\t\t");
			System.out.println(dto.getTitle());
		}
		System.out.println("--------------------------------------------------------------------------------------");
		
		System.out.println("### 메뉴를 선택하세요.");
		System.out.println("1.전체구매  2.선택구매  3.전체삭제  4.선택삭제  5.쇼핑계속");
		
		System.out.print("번호 입력: ");
		int menuNo = keyboard.readInt();
		System.out.println();
		
		if (menuNo == 1) {
			장바구니아이템전체구매(sessionId);
			// orderController.내주문내역보기(sessionId);
		} else if (menuNo == 2) {
			장바구니아이템선택구매(sessionId);
			// orderController.내주문내역보기(sessionId);
		} else if (menuNo == 3) {
			장바구니아이템전체삭제(sessionId);
			내장바구니보기(sessionId);
		} else if (menuNo == 4) {
			장바구니아이템선택삭제(sessionId);
			내장바구니보기(sessionId);
		} else if (menuNo == 5) {
			return;
		}
	}
	
	public void 장바구니아이템전체구매(long sessionId) throws SQLException {
		
	}
	public void 장바구니아이템선택구매(long sessionId) throws SQLException {
		
	}
	public void 장바구니아이템전체삭제(long sessionId) throws SQLException {
		System.out.println("<< 장바구니 아이템 전체 삭제 >>");
		System.out.println("### 장바구니에 저장된 모든 아이템을 삭제합니다.");
		
		cartItemService.deleteAllMyCartItems(sessionId);
	}
	public void 장바구니아이템선택삭제(long sessionId) throws SQLException {
		
	}
}





