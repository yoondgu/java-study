package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.User;

/**
 * STORE_USERS 테이블에 대한 CRUD 기능을 제공하는 클래스다.
 * @author lee_e
 *
 */
public class UserDao {
	
	private static UserDao instance = new UserDao();
	private UserDao() {}
	
	/**
	 * UserDao객체를 반환한다.
	 * @return UserDao
	 */
	public static UserDao getInstance() {
		return instance;
	}

	/**
	 * 사용자정보를 제공받아서 테이블에 저장한다.
	 * @param user 사용자정보
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertUser(User user) throws SQLException {
		String sql = "insert into store_users(user_no, user_email, user_password, user_name, user_tel) "
				   + "values(store_users_seq.nextval, ?, ?, ?, ?)";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getTel());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();		
	}
	
	/**
	 * 이메일을 제공받아서 이메일이 일치하는 사용자정보를 조회해서 반환한다.
	 * @param email 이메일
	 * @return 사용자정보, 이메일이 일치하는 사용자정보가 없으면 null을 반환한다.
	 * @throws SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public User getUserByEmail(String email) throws SQLException {
		String sql = "select * "
				   + "from store_users "
				   + "where user_email = ? ";
		
		User user = null;

		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, email);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			user = new User();
			user.setNo(rs.getInt("user_no"));
			user.setEmail(rs.getString("user_email"));
			user.setPassword(rs.getString("user_password"));
			user.setName(rs.getString("user_name"));
			user.setTel(rs.getString("user_tel"));
			user.setPoint(rs.getInt("user_point"));
			user.setDeleted(rs.getString("user_deleted"));
			user.setCreatedDate(rs.getDate("user_created_date"));
			user.setUpdatedDate(rs.getDate("user_updated_date"));
		}
		
		rs.close();
		pstmt.close();
		connection.close();		
		
		return user;
	}

	/**
	 * 사용자번호를 제공받아서 사용자번호가 일치하는 사용자정보를 조회해서 반환한다.
	 * @param userNo
	 * @return 사용자정보, 사용자번호가 일치하는 사용자가 없으면 null을 반환한다.
	 * @throws SQLException SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public User getUserByNo(int userNo) throws SQLException {
		String sql = "select * "
				   + "from store_users "
				   + "where user_no = ? ";
		
		User user = null;

		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			user = new User();
			user.setNo(rs.getInt("user_no"));
			user.setEmail(rs.getString("user_email"));
			user.setPassword(rs.getString("user_password"));
			user.setName(rs.getString("user_name"));
			user.setTel(rs.getString("user_tel"));
			user.setPoint(rs.getInt("user_point"));
			user.setDeleted(rs.getString("user_deleted"));
			user.setCreatedDate(rs.getDate("user_created_date"));
			user.setUpdatedDate(rs.getDate("user_updated_date"));
		}
		
		rs.close();
		pstmt.close();
		connection.close();		
		
		return user;
	}
	
	/**
	 * 변경된 사용자정보를 전달받아서 데이터베이스에 반영시킨다.
	 * @param user 사용자정보
	 * @throws SQLException SQLException 데이터베이스 엑세스 작업중 오류가 발생하면 이 예외를 던진다.
	 */
	public void updateUser(User user) throws SQLException {
		String sql = "update store_users "
					+ "set "
					+ "user_password = ?, "
					+ "user_tel = ?, "
					+ "user_point = ?, "
					+ "user_deleted = ?, "
					+ "user_updated_date = sysdate "
					+ "where user_no = ? ";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getTel());
		pstmt.setInt(3, user.getPoint());
		pstmt.setString(4, user.getDeleted());
		pstmt.setInt(5, user.getNo());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
		
	}
}

