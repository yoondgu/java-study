package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.PointHistory;

/**
 * STORE_USER_POINT_HISTORIES 테이블에 대한 CRUD 작업을 제공하는 클래스
 * @author doyoung
 *
 */
public class PointHistoryDao {

	private static PointHistoryDao instance = new PointHistoryDao();
	private PointHistoryDao() {}
	public static PointHistoryDao getInstance() {
		return instance;
	}
	
	/**
	 * 주문으로 인한 포인트 변경정보를 전달받아서 테이블에 저장한다.
	 * @param pointHistory 포인트 변경정보
	 * @throws SQLException SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertPointHistory(int userNo, int orderNo, String reason, int amount) throws SQLException {
		String sql = "insert into store_user_point_histories "
					+ "(point_history_no, user_no, order_no, point_history_reason, point_amount) "
					+ "values "
					+ "(store_pointhistories_seq.nextval, ?, ?, ?, ?)";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, userNo);
		pstmt.setInt(2, orderNo);
		pstmt.setString(3, reason);
		pstmt.setInt(4, amount);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}

	/**
	 * 주문 외 경우의 포인트 변경정보를 전달받아서 테이블에 저장한다.
	 * @param pointHistory 포인트 변경정보
	 * @throws SQLException SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public void insertPointHistory(int userNo,  String reason, int amount) throws SQLException {
		String sql = "insert into store_user_point_histories "
				+ "(point_history_no, user_no, point_history_reason, point_amount) "
				+ "values "
				+ "(store_pointhistories_seq.nextval, ?, ?, ?)";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, userNo);
		pstmt.setString(2, reason);
		pstmt.setInt(3, amount);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public List<PointHistory> getPointHistoriesByUserNo(int userNo) throws SQLException {
		String sql = "select * from store_user_point_histories "
					+ "where user_no = ?"
					+ "order by point_history_no desc";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		
		ResultSet rs = pstmt.executeQuery();
		List<PointHistory> histories = new ArrayList<>();
		while (rs.next()) {
			PointHistory history = new PointHistory();
			history.setNo(rs.getInt("point_history_no"));
			history.setUserNo(userNo);
			history.setOrderNo(rs.getInt("order_no"));
			history.setReason(rs.getString("point_history_reason"));
			history.setAmount(rs.getInt("point_amount"));
			history.setCreatedDate(rs.getDate("point_history_created_date"));
			history.setUpdatedDate(rs.getDate("point_history_updated_date"));
			histories.add(history);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return histories;
	}
}
