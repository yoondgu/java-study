package kr.co.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.bookstore.dao.LoginedUserDao;
import kr.co.bookstore.dao.PointDao;
import kr.co.bookstore.dao.UserDao;
import kr.co.bookstore.vo.LoginedUser;
import kr.co.bookstore.vo.Order;
import kr.co.bookstore.vo.PointHistory;
import kr.co.bookstore.vo.User;

/**
 * 회원관련 서비스가 구현된 클래스
 * @author doyoung
 *
 */
public class UserService {

	private static UserService instance = new UserService();
	private UserService() {}
	
	/**
	 * UserService 객체를 반환한다.
	 * @return UserService
	 */
	public static UserService getInstance() {
		return instance;
	}
	
	private UserDao userDao = UserDao.getInstance();
	private PointDao pointDao = PointDao.getInstance();
	private LoginedUserDao loginedUserDao = LoginedUserDao.getInstance();
	
	/**
	 * 회원가입 서비스를 제공한다.
	 * @param user 신규 회원 정보를 포함하고 있는 User 객체
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void registerUser(User user) throws SQLException {
		User savedUser = userDao.getUserByEmail(user.getEmail());
		
		if (savedUser != null) {
			throw new RuntimeException("이미 사용중인 아이디입니다.");
		} else {
			userDao.insertUser(user);
		}
	}
	
	/**
	 * 이메일과 비밀번호를 전달받아서 사용자 인증서비스를 제공한다.
	 * <p>
	 * 사용자 인증이 완료되면 로그인정보를 저장하고, 로그인 일련번호를 반환한다.
	 * @param email
	 * @param password
	 * @return 로그인 일련번호
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public long login(String email, String password) throws SQLException {
		long sessionId = -1;
		
		User savedUser = userDao.getUserByEmail(email);
		if (savedUser == null) {
			throw new RuntimeException("아이디와 비밀번호가 올바르지 않습니다.");
		} else if ("Y".equals(savedUser.getDeleted())) {
			throw new RuntimeException("아이디와 비밀번호가 올바르지 않습니다.");
		} else if (!savedUser.getPassword().equals(password)) {
			throw new RuntimeException("아이디와 비밀번호가 올바르지 않습니다.");
		}
		
		// 로그인 인증에 성공하면 유닉스타임을 조회해서 sessionId에 대입시키고, 그 sessionId를 가진 로그인정보객체를 만들어 데이터베이스에 저장한다.
		sessionId = System.currentTimeMillis();
		LoginedUser loginedUser = new LoginedUser(sessionId, savedUser.getNo());
		loginedUserDao.insertLoginedUser(loginedUser);
		
		return sessionId;
	}

	/**
	 * 로그인 일련번호(세션아이디)를 전달해 로그인된 사용자정보를 로그아웃 처리한다.
	 * @param loginedUser
	 * @return 로그인 일련번호(세션아이디)
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void logout(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser != null) {
			loginedUser.setStatus("N");
			
			loginedUserDao.updateLoginedUser(loginedUser);
		}
		
	}

	/**
	 * ResultSet 객체에서 현재 커서가 위치한 행의 데이터를 가져와서 User객체에 저장하고 반환한다.
	 * @param rs ResultSet
	 * @return 현재 커서가 위치한 행의 값을 저장하고 있는 User 객체
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public User getUserInfo(long sessionId) throws SQLException {
		User user = null;

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

	public void changeUserPwd(long sessionId, String changePwd) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}
		
		User user = userDao.getUserByNo(loginedUser.getUserNo());
		user.setPassword(changePwd);
		
		userDao.updateUser(user);
	}

	public void changePoint(Order order) throws SQLException {
		
		// 사용자 정보에 포인트 변경액 반영
		User user = userDao.getUserByNo(order.getUserNo());
		int currentPoint = user.getPoint();
		int changePoint = currentPoint + order.getDepositPoint() - order.getUsedPoint();
		user.setPoint(changePoint);
		userDao.updateUser(user);
		
		// 포인트 변경이력정보 저장
		if (order.getUsedPoint() > 0) {
			PointHistory pointHistory = new PointHistory();
			pointHistory.setUserNo(user.getNo());
			pointHistory.setOrderNo(order.getNo());
			pointHistory.setHistoryReason("포인트 사용");
			pointHistory.setAmount(currentPoint - order.getUsedPoint());
			pointDao.insertPointHistory(pointHistory);
		}
		
		if (order.getDepositPoint() > 0) {
			PointHistory pointHistory = new PointHistory();
			pointHistory.setUserNo(user.getNo());
			pointHistory.setOrderNo(order.getNo());
			pointHistory.setHistoryReason("구매 적립");
			pointHistory.setAmount(changePoint);
			pointDao.insertPointHistory(pointHistory);
		}
	}

	public List<PointHistory> getMyPointHistories(long sessionId) throws SQLException {
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}		
		
		return pointDao.getAllPointHistoriesByUser(loginedUser.getUserNo());
	}

	
}
