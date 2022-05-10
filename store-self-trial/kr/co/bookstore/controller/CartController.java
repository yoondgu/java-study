package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.CartItemDao;
import kr.co.bookstore.dto.CartItemDto;
import kr.co.bookstore.service.CartItemService;
import kr.co.bookstore.service.OrderService;
import kr.co.bookstore.service.UserService;
import kr.co.bookstore.util.KeyboardReader;
import kr.co.bookstore.vo.CartItem;
import kr.co.bookstore.vo.User;

public class CartController {

	private static CartController instance = new CartController();
	private CartController() {}
	public static CartController getInstance() {
		return instance;
	}	
	
	private KeyboardReader keyboardReader = KeyboardReader.getInstance();
	private CartItemService cartItemService = CartItemService.getInstance();
	private OrderService orderService = OrderService.getInstance();
	private UserService userService = UserService.getInstance();
	
	private CartItemDao cartItemDao = CartItemDao.getInstance();
	
	public void 장바구니담기(int bookNo, long sessionId) throws SQLException {
		cartItemService.addBookIntoCart(bookNo, sessionId);
	}
	
	public void 내장바구니보기(long sessionId) throws SQLException {
		System.out.println("<<  내 장바구니 보기  >>");
		System.out.println("### 장바구니에 저장된 아이템 정보를 확인해보세요.");
		
		List<CartItemDto> cartItems = cartItemService.getMyCartItems(sessionId);
		if (cartItems.isEmpty()) {
			System.out.println("[처리 완료] 장바구니에 저장된 아이템이 없습니다.");
			return;
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
	    System.out.println("아이템번호\t가격\t할인가격\t수량\t구매가격\t\t제목");
	    System.out.println("--------------------------------------------------------------------------------------");
	      for (CartItemDto dto : cartItems) {
	         System.out.print(dto.getItemNo() + "\t");
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
		int menuNo = keyboardReader.readInt();
		
		if (menuNo == 1) {
			장바구니아이템전체구매(sessionId);
			내장바구니보기(sessionId);
		} else if (menuNo == 2) {
			장바구니아이템선택구매(sessionId);
			내장바구니보기(sessionId);
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
		System.out.println("<<  장바구니 아이템 전체 구매  >>");
		System.out.println("### 장바구니에 저장된 모든 아이템을 구매하고, 장바구니 아이템은 모두 삭제됩니다.");
		
		User user = userService.getUserInfo(sessionId);
		System.out.println("[현재 보유 포인트 금액: " + user.getPoint() + "원]");
		System.out.print("사용할 포인트 금액 입력: ");
		int usePoint = keyboardReader.readInt();
		
		orderService.orderAllCartItems(sessionId, usePoint);
		cartItemService.deleteAllMyCartItems(sessionId);
		
		System.out.println("[처리 완료] 주문이 완료되었습니다.");

	}
	
	public void 장바구니아이템선택구매(long sessionId) throws SQLException {
		System.out.println("<<  장바구니 아이템 선택 구매  >>");
		System.out.println("### 장바구니 아이템 번호를 입력하여 선택 구매합니다. 구매한 장바구니 아이템은 삭제됩니다.");
		
		System.out.print("번호 입력: ");
		int itemNo = keyboardReader.readInt();
		CartItem cartItem = cartItemDao.getCartItemByItemNo(itemNo);
		
		System.out.print("수량 입력: ");
		int quantity = keyboardReader.readInt();
		
		User user = userService.getUserInfo(sessionId);
		System.out.println("[현재 보유 포인트 금액: " + user.getPoint() + "원]");
		System.out.print("사용할 포인트 금액 입력: ");
		int usePoint = keyboardReader.readInt();
		
		if (cartItem != null) {
			orderService.orderOneBook(cartItem.getBookNo(), sessionId, usePoint, quantity);
			cartItemService.deleteCartItemsByItemNo(itemNo, sessionId);
		}
		
		System.out.println("[처리 완료] 주문이 완료되었습니다.");
	}
	
	public void 장바구니아이템전체삭제(long sessionId) throws SQLException {
		System.out.println("<<  장바구니 아이템 전체 삭제  >>");
		System.out.println("### 장바구니에 저장된 모든 아이템을 삭제합니다.");
		
		cartItemService.deleteAllMyCartItems(sessionId);
		
		System.out.println("[처리 완료] 장바구니 아이템이 삭제되었습니다.");
	}
	
	public void 장바구니아이템선택삭제(long sessionId) throws SQLException {
		System.out.println("<<  장바구니 아이템 선택 삭제  >>");
		System.out.println("### 지정된 번호의 장바구니 아이템을 삭제합니다.");
		
		System.out.print("번호 입력: ");
		int itemNo = keyboardReader.readInt();
		cartItemService.deleteCartItemsByItemNo(itemNo, sessionId);
		
		System.out.println("[처리 완료] 장바구니 아이템이 삭제되었습니다.");
	}

}
