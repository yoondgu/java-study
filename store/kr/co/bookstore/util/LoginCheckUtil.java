package kr.co.bookstore.util;

import java.sql.SQLException;

import kr.co.bookstore.dao.LoginedUserDao;
import kr.co.bookstore.vo.LoginedUser;

public class LoginCheckUtil {

	/**
	 * 로그인 일련번호를 전달받아서 로그인여부를 체크하고, 로그인되지 않은 요청일 경우 예외를 던진다.
	 * @param sessionId 로그인 일련번호
	 * @return 로그인된 사용자 정보
	 * @throws SQLException
	 */
	public static LoginedUser getLoginedUser(long sessionId) throws SQLException {
		LoginedUserDao loginedUserDao = LoginedUserDao.getInstance();
		
		LoginedUser loginedUser = loginedUserDao.getLoginedUserBySessionId(sessionId);
		if (loginedUser == null) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");
		}
		if ("N".equals(loginedUser.getStatus())) {
			throw new RuntimeException("로그인 일련번호가 유효하지 않습니다.");			
		}
		
		return loginedUser;
	}
}
