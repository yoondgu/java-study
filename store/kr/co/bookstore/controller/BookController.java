package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.service.BookService;
import kr.co.bookstore.service.CartItemService;
import kr.co.bookstore.service.OrderService;
import kr.co.bookstore.util.KeyboardReader;
import kr.co.bookstore.vo.Book;

public class BookController {

	private static BookController instance = new BookController();
	private BookController() {}
	public static BookController getInstance() {
		return instance;
	}
	
	private KeyboardReader keyboard = KeyboardReader.getInstance(); 
	
	private CartController cartController = CartController.getInstance();
	private OrderController orderController = OrderController.getInstance();
	
	private CartItemService cartItemService = CartItemService.getInstance();
	private BookService bookService = BookService.getInstance();
	private OrderService orderService = OrderService.getInstance();
	
	
	public void 전체도서리스트() throws SQLException {
		System.out.println("<<  전체 도서 리스트 >>");
		System.out.println("### 전체 도서 리스트를 확인해보세요");
		
		List<Book> books = bookService.getAllBooks();
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("책번호\t\t할인가격\t\t저자\t\t제목");
		System.out.println("--------------------------------------------------------------------------------------");
		for (Book book : books) {
			System.out.print(book.getNo() + "\t\t");
			System.out.print(book.getDiscountPrice() + "\t\t");
			System.out.print(book.getAuthor() + "\t\t");
			System.out.println(book.getTitle());
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
	
	public void 상세조회(long sessionId) throws SQLException{
		System.out.println("<< 도서 상세 조회 >>");
		System.out.println("### 책번호를 입력해서 상세정보를 확인하세요.");
		
		System.out.print("책번호 입력: ");
		int bookNo = keyboard.readInt();
		
		Book book = bookService.getBookInfo(bookNo);
		if (book == null) {
			System.out.println("[입력오류] 책번호가 올바르지 않습니다.");
			return;
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("책 번호: " + book.getNo());
		System.out.println("책 제목: " + book.getTitle());
		System.out.println("책 저자: " + book.getAuthor());
		System.out.println("책 출판사: " + book.getPublisher());
		System.out.println("책 가격: " + book.getPrice() + " 원");
		System.out.println("책 할인가격: " + book.getDiscountPrice() + " 원");
		System.out.println("책 판매여부: " + ("Y".equals(book.getOnSell()) ? "판매중" : "재고없음"));
		System.out.println("책 재고수량: " + book.getStock());
		System.out.println();
		System.out.println(book.getDescription());
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println();
		
		System.out.println("### 메뉴를 선택하세요.");
		System.out.println("1.바로구매  2.장바구니  3.쇼핑계속");
		System.out.print("번호 입력: ");
		int menuNo = keyboard.readInt();
		
		if (menuNo == 1) {
			System.out.print("구매수량 입력: ");
			int quantity = keyboard.readInt();
			System.out.print("사용할 포인트 입력: ");
			int usedPoint = keyboard.readInt();
			
			if (quantity > book.getStock()) {
				System.out.println("[입력오류] 구매수량이 현재 재고량보다 큽니다.");
				return;
			}
			
			orderService.buyBookByNo(bookNo, quantity, usedPoint, sessionId);
			System.out.println("[처리 완료] 구매가 완료되었습니다.");
			orderController.내구매내역보기(sessionId);
		} else if (menuNo == 2) {
			cartItemService.addBookIntoCart(bookNo, sessionId);
			cartController.내장바구니보기(sessionId);
		} else if (menuNo == 3) {
			전체도서리스트();
		}
		
	}
	
}









