package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.LoginedUserDao;
import kr.co.bookstore.dao.PointHistoryDao;
import kr.co.bookstore.dao.UserDao;
import kr.co.bookstore.util.LoginCheckUtil;
import kr.co.bookstore.vo.LoginedUser;
import kr.co.bookstore.vo.PointHistory;
import kr.co.bookstore.vo.User;

/**
 * 회원관련 서비스가 구현된 클래스다.
 * @author lee_e
 *
 */
public class UserService {

	private static UserService instance = new UserService();
	private UserService() {}
	/**
	 * UserService객체를 반환한다
	 * @return UserService
	 */
	public static UserService getInstance() {
		return instance;
	}
	
	private UserDao userDao = UserDao.getInstance();
	private LoginedUserDao loginedUserDao = LoginedUserDao.getInstance();
	private PointHistoryDao pointHistoryDao = PointHistoryDao.getInstance();
		
	/**
	 * 회원가입 서비스를 제공한다.
	 * @param user 신규 회원 정보를 포함하고 있는 User객체
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void registerUser(User user) throws SQLException {
		User savedUser = userDao.getUserByEmail(user.getEmail());
		if (savedUser != null) {
			throw new RuntimeException("이미 사용중인 아이디입니다.");
		}
		userDao.insertUser(user);
	}
	
	/**
	 * 이메일과 비밀번호를 전달받아서 사용자 인증서비스를 제공한다.
	 * <p>
	 * 사용자 인증이 완료되면 로그인정보를 저장하고, 로그인 일련번호를 반환한다.
	 * @param email 이메일
	 * @param password 비밀번호
	 * @return 로그인 일련번호
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public long login(String email, String password) throws SQLException {
		long sessionId = -1;
		
		User savedUser = userDao.getUserByEmail(email);
		if (savedUser == null) {
			throw new RuntimeException("이메일 혹은 비밀번호가 올바르지 않습니다.");
		}
		if ("Y".equals(savedUser.getDeleted())) {
			throw new RuntimeException("삭제된 사용자 계정입니다.");
		}
		if (!savedUser.getPassword().equals(password)) {
			throw new RuntimeException("이메일 혹은 비밀번호가 올바르지 않습니다.");
		}
		
		// 유닉스타입을 조회해서 sessionId에 대입시킨다.
		sessionId = System.currentTimeMillis();
		LoginedUser loginedUser = new LoginedUser(sessionId, savedUser.getNo());
		loginedUserDao.insertLoginedUser(loginedUser);
		
		return sessionId;
	}
	
	/**
	 * 로그인 일련번호와 일치하는 로그인정보를 로그아웃상태로 변경하는 서비스를 제공한다.
	 * @param sessionId 로그인 일련번호
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void logout(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser != null) {
			loginedUser.setStatus("N");
			
			loginedUserDao.updateLoginedUser(loginedUser);
		}		
	}
	
	/**
	 * 로그인 일련번호와 일치하는 로그인정보로 사용자정보를 조회해서 반환하는 서비스를 제공한다.
	 * @param sessionId 로그인 일련번호
	 * @return 사용자정보
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public User getUserInfo(long sessionId) throws SQLException {
		User user = null;
		
		// 로그인 일련번호에 대한 로그인정보(일련번호, 로그인상태, 유저번호가 포함되어 있다.) 조회
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");			
		}
		
		int userNo = loginedUser.getUserNo();
		user = userDao.getUserByNo(userNo);		
		
		return user;
	}
	
	/**
	 * 사용자의 포인트를 변경하고, 변경이력을 남기는 서비스를 제공한다.
	 * @param userNo 사용자번호
	 * @param orderNo 주문번호, 주문과 관련이 없는 포인트변경인 경우 orderNo는 0이다.
	 * @param reason 포인트 변경 사유
	 * @param amount 변경된 포인트 금액, 포인트를 사용하는 경우 음수, 포인트가 적립되는 경우 양수다.
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void changeUserPoint(int userNo, int orderNo, String reason, int amount) throws SQLException {
		if (amount == 0) {
			return;
		}
		
		User user = userDao.getUserByNo(userNo);
		user.setPoint(user.getPoint() + amount);
		userDao.updateUser(user);
		if (orderNo != 0) {		// orderNo에 0이 저장되면 FK 제약조건 위배한다.
			pointHistoryDao.insertPointHistory(userNo, orderNo, reason, amount);
		} else {
			pointHistoryDao.insertPointHistory(userNo, reason, amount);
		}
	}
	
	public List<PointHistory> getMyPointHistories(long sessionId) throws SQLException {
		LoginedUser loginedUser = LoginCheckUtil.getLoginedUser(sessionId);
		
		return pointHistoryDao.getPointHistoriesByUserNo(loginedUser.getUserNo());
	}
	
	public void changePassword(String password, long sessionId) throws SQLException {
		LoginedUser loginedUser = LoginCheckUtil.getLoginedUser(sessionId);
		
		User user = userDao.getUserByNo(loginedUser.getUserNo());
		user.setPassword(password);
		userDao.updateUser(user);
	}
	
}








