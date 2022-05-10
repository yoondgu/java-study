package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.service.BookService;
import kr.co.bookstore.util.KeyboardReader;
import kr.co.bookstore.vo.Book;

public class BookController {

	private static BookController instance = new BookController();
	private BookController() {}
	public static BookController getInstance() {
		return instance;
	}
	
	private KeyboardReader keyboardReader = KeyboardReader.getInstance();
	
	private CartController cartController = CartController.getInstance();
	private OrderController orderController = OrderController.getInstance();
	
	private BookService bookService = BookService.getInstance();
	
	public void 전체도서리스트() throws SQLException {
		System.out.println("<<  전체 도서 리스트  >>");
		System.out.println("### 전체 도서 리스트를 확인해보세요.");
		
		List<Book> bookList = bookService.getAllBooks();

		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("책번호\t할인가격\t저자\t\t제목");
		System.out.println("--------------------------------------------------------------------------------------");
		for (Book book : bookList) {
			System.out.print(book.getNo() + "\t");
			System.out.print(book.getDiscountPrice() + "\t");
			System.out.print(book.getAuthor() + "\t\t");
			System.out.println(book.getTitle());
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
	
	public void 상세조회(long sessionId) throws SQLException {
		System.out.println("<<  도서 상세 조회  >>");
		System.out.println("### 책번호를 입력해서 상세정보를 확인하세요.");
		
		System.out.print("책번호 입력: ");
		int bookNo = keyboardReader.readInt();
		
		Book book = bookService.getBookInfo(bookNo);
		if (book == null) {
			System.out.println("[입력오류] 책번호가 올바르지 않습니다.");
			return;
		}
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("책번호: " + book.getNo());
		System.out.println("책제목: " + book.getTitle());
		System.out.println("저자: " + book.getAuthor());
		System.out.println("출판사: " + book.getPublisher());
		System.out.println("판매가격: " + book.getPrice() + "원");
		System.out.println("할인가격: " + book.getDiscountPrice() + "원");
		System.out.println("판매여부: " + ("Y".equals(book.getOnSell()) ? "판매중" : "재고없음"));
		System.out.println("재고량: " + book.getStock());
		System.out.println();
		System.out.println(book.getDescription());
		System.out.println("--------------------------------------------------------------------------------------");

		System.out.println("### 메뉴를 선택하세요.");
		System.out.println("1. 바로구매  2.장바구니  3.쇼핑계속");
		System.out.print("번호입력: ");
		int menuNo = keyboardReader.readInt();
		
		if (menuNo == 1) {
			// 책 번호와 로그인 일련번호를 OrderService의 바로구매하기(책번호, 로그인일련번호) 메소드를 실행한다.
			orderController.바로구매하기(bookNo, sessionId);
		} else if (menuNo == 2) {
			// 책번호와 로그인 일련번호를 CartService의 addBookIntoCart(책번호, 로그인일련번호) 메소드를 실행한다.		
			cartController.장바구니담기(bookNo, sessionId);
			// 장바구니 목록을 표현하는 CartItemController의 장바구니목록(로그인 일련번호) 메소드를 실행한다.
			// sessionId는 상세정보 조회할 때 받았음. 
			cartController.내장바구니보기(sessionId);
			
		} else if (menuNo == 3) {
			전체도서리스트();
		}
	}
	
}
