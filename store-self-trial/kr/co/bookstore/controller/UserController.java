package kr.co.bookstore.controller;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.service.UserService;
import kr.co.bookstore.util.KeyboardReader;
import kr.co.bookstore.vo.PointHistory;
import kr.co.bookstore.vo.User;

/**
 * 사용자와 상호작용하는 기능이 구현된 클래스
 * @author doyoung
 *
 */
public class UserController {

	private static UserController instance = new UserController();
	private UserController() {}
	/**
	 * UserController 객체를 반환한다.
	 * @return UserController
	 */
	public static UserController getInstance() {
		return instance;
	}
	
	KeyboardReader keyboardReader = KeyboardReader.getInstance();
	UserService userService = UserService.getInstance();
	
	public void 회원가입() throws SQLException {
		System.out.println("<<  회원가입  >>");
		System.out.println("### 이메일, 비밀번호, 이름, 전화번호을 입력하세요.");
		
		System.out.print("이메일 입력: ");
		String email = keyboardReader.readString();
		System.out.print("비밀번호 입력: ");
		String password = keyboardReader.readString();
		System.out.print("이름 입력: ");
		String name = keyboardReader.readString();
		System.out.print("전화번호 입력: ");
		String tel = keyboardReader.readString();
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		user.setTel(tel);
		
		userService.registerUser(user);
		
		System.out.println("[처리완료] " + user.getName() + "님 회원가입이 완료되었습니다.");
	}
	
	public long 로그인() throws SQLException {
		System.out.println("<<  로그인  >>");
		System.out.println("### 이메일, 비밀번호를 입력하세요.");
		
		long sessionId = -1;
		
		System.out.print("이메일 입력: ");
		String email = keyboardReader.readString();
		System.out.print("비밀번호 입력: ");
		String password = keyboardReader.readString();
		
		sessionId = userService.login(email, password);
//		if (sessionId == -1) {										사실상 -1이 반환될 일은 없다.
//			System.out.println("[처리완료] 로그인에 실패하였습니다.");
//		}
		System.out.println("[처리완료] 로그인이 완료되었습니다.");
		
		return sessionId;
	}
	
	public void 로그아웃(long sessionId) throws SQLException {
		System.out.println("<<  로그아웃  >>");
		System.out.println("### 시스템에서 로그아웃됩니다.");
		
		userService.logout(sessionId);
		System.out.println("[처리완료] 로그아웃이 완료되었습니다.");
	}
	public void 내정보조회(long sessionId) throws SQLException {
		System.out.println("<<  내정보조회  >>");
		System.out.println("### 내 정보를 확인해보세요.");
		
		User user = userService.getUserInfo(sessionId);
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("번호: " + user.getNo());
		System.out.println("이메일: " + user.getEmail());
		System.out.println("이름: " + user.getName());
		System.out.println("전화번호: " + user.getTel());
		System.out.println("포인트: " + user.getPoint());
		System.out.println("수정일자: " + user.getUpdatedDate());
		System.out.println("등록일자: " + user.getCreatedDate());
		System.out.println("--------------------------------------------------------------------------------------");

		System.out.println("[처리완료] 내정보조회가 완료되었습니다.");
	}
	
	public void 비밀번호변경(long sessionId) throws SQLException {
		System.out.println("<< 비밀번호 변경 >>");
		System.out.println("### 비밀번호를 변경합니다.");
		
		System.out.print("변경할 비밀번호 입력: ");
		String changePwd = keyboardReader.readString();
		
		userService.changeUserPwd(sessionId, changePwd);
		System.out.println("[처리완료] 비밀번호가 변경되었습니다.");
	}
	
	public void 포인트이력조회(long sessionId) throws SQLException {
		System.out.println("<< 포인트 변경이력 조회 >>");
		System.out.println("### 포인트 변경이력을 조회합니다.");
		
		List<PointHistory> pointHistories = userService.getMyPointHistories(sessionId);
		
		if (pointHistories.isEmpty()) {
			System.out.println("[처리 완료] 로그인된 사용자의 포인트 변경이력이 존재하지 않습니다.");
			return;
		}
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("주문번호\t\t변경내용\t\t변경금액\t등록일");
		for (PointHistory pointHistory : pointHistories) {
			System.out.print(pointHistory.getOrderNo() + "\t");
			System.out.print(pointHistory.getHistoryReason() + "\t");
			System.out.print(pointHistory.getAmount() + "\t");
			System.out.println(pointHistory.getCreatedDate());
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
}
