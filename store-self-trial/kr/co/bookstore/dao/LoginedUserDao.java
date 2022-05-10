package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.LoginedUser;

/**
 * STORE_LOGINED_USERS에 대한 CRUD 기능을 제공한다.
 * @author doyoung
 *
 */
public class LoginedUserDao {

	private static LoginedUserDao instance = new LoginedUserDao();
	private LoginedUserDao() {}
	/**
	 * LoginedUserDao 객체를 반환한다.
	 * @return LoginedUserDao
	 */
	public static LoginedUserDao getInstance() {
		return instance;
	}
	
	/**
	 * 로그인 일련번호(세션아이디), 사용자번호가 포함된 정보를 전달받아서 저장한다.
	 * @param loginedUser 로그인된 사용자정보
	 * @exception SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertLoginedUser(LoginedUser loginedUser) throws SQLException {
		String sql = "insert into store_logined_users(login_session_id, user_no) values(?, ?) ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, loginedUser.getSessionId());
		pstmt.setInt(2, loginedUser.getUserNo());
		
		pstmt.executeUpdate();
		pstmt.close();
		connection.close();
	}
	
	/**
	 * 로그인 일련번호(세션아이디)와 일치하는 로그인된 사용자정보를 반환한다.
	 * @param sessionId 로그인 일련번호
	 * @return 로그인된 사용자정보
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public LoginedUser getLoginedUserBySessionId(long sessionId) throws SQLException {
		String sql = "select login_session_id, user_no, last_login_date, login_status "
					+ "from store_logined_users "
					+ "where login_session_id = ? ";
				
		LoginedUser loginedUser = null;
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, sessionId);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			loginedUser = new LoginedUser();
			loginedUser.setSessionId(sessionId);
			loginedUser.setUserNo(rs.getInt("user_no"));
			loginedUser.setLastLoginDate(rs.getDate("last_login_date"));
			loginedUser.setStatus(rs.getString("login_status"));
		}
		
		 rs.close();
		 pstmt.close();
		 connection.close();
		
		return loginedUser;
	}
	

	/**
	 * 변경된 로그인 정보를 전달받아서 테이블에 반영한다.
	 * @param loginedUser 변경된 로그인 정보
	 */
	public void updateLoginedUser(LoginedUser loginedUser) throws SQLException {
		String sql = "update store_logined_users "
					+ "set "
					+ "		login_status = ? "
					+ "where login_session_id = ? ";
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, loginedUser.getStatus());
		pstmt.setLong(2, loginedUser.getSessionId());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
}
