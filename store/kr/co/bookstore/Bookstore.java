package kr.co.bookstore;

import java.sql.SQLException;

import kr.co.bookstore.controller.BookController;
import kr.co.bookstore.controller.CartController;
import kr.co.bookstore.controller.OrderController;
import kr.co.bookstore.controller.UserController;
import kr.co.bookstore.util.KeyboardReader;

public class Bookstore {
	
	private long sessionId = -1;
	
	private KeyboardReader keyboard = KeyboardReader.getInstance();
	
	private BookController bookController = BookController.getInstance();
	private CartController cartController = CartController.getInstance();
	private UserController userController = UserController.getInstance();
	private OrderController orderController = OrderController.getInstance();
	
	public Bookstore() {
		menu();
	}
	
	private void menu() {
		try {
			System.out.println("[북스토어 프로그램]");
			System.out.println("--------------------------------------------------------------------------------------");
			if (sessionId == -1) {
				System.out.println("1.로그인  2.회원가입  3.도서조회  0.종료");				
			} else{
				System.out.println("3.도서조회  4.도서상세  5.마이메뉴  6.로그아웃  0.종료");								
			}
			System.out.println("--------------------------------------------------------------------------------------");
		
			System.out.print("메뉴 선택: ");
			int menuNo = keyboard.readInt();
			System.out.println();
			
			switch (menuNo) {
				case 1:
					sessionId = userController.로그인(); 
					break;
				case 2: 
					userController.회원가입(); 
					break;
				case 3: 
					bookController.전체도서리스트(); 
					break;
				case 4: 
					bookController.상세조회(sessionId); 
					break;
				case 5:
					myMenu();
					break;
				case 6:
					userController.로그아웃(sessionId); 
					sessionId = -1;
					break;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		menu();
	}
	
	private void myMenu() throws SQLException {
		
		if (sessionId == -1) {
			System.err.println("[접근거부] 로그인 후 사용가능한 메뉴입니다.");
			return;
		}
		
		System.out.println("[마이 메뉴]");
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("1.내 정보  2.주문내역  3.장바구니  4.포인트  5.비밀번호변경  0.종료");								
		System.out.println("--------------------------------------------------------------------------------------");
	
		System.out.print("메뉴 선택: ");
		int menuNo = keyboard.readInt();
		System.out.println();
		
		switch (menuNo) {
			case 1:
				userController.내정보보기(sessionId);
				break;
			case 2: 
				orderController.내구매내역보기(sessionId);
				break;
			case 3:
				cartController.내장바구니보기(sessionId);
				break;
			case 4:
				userController.내포인트이력보기(sessionId);
				break;
			case 5:
				userController.비밀번호변경(sessionId);
				break;
			case 0:
				return;
		}
		System.out.println();
		System.out.println();
		System.out.println();
		myMenu();
	}

	public static void main(String[] args) {
		new Bookstore();
	}
}
