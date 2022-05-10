package kr.co.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.bookstore.util.ConnectionUtil;
import kr.co.bookstore.vo.PointHistory;

public class PointDao {

	private static PointDao instance = new PointDao();
	private PointDao() {}
	public static PointDao getInstance() {
		return instance;
	}
	
	public void insertPointHistory(PointHistory pointHistory) throws SQLException {
		String sql = "insert into store_user_point_histories(point_history_no, user_no, order_no, "
					+ "point_history_reason, point_amount) "
					+ "values(store_pointhistories_seq.nextval, ?, ?, ?, ?)";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, pointHistory.getUserNo());
		pstmt.setLong(2, pointHistory.getOrderNo());
		pstmt.setString(3, pointHistory.getHistoryReason());
		pstmt.setInt(4, pointHistory.getAmount());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public List<PointHistory> getAllPointHistoriesByUser(int userNo) throws SQLException {
		String sql = "select * from store_user_point_histories "
					+ "where user_no = ?"
					+ "order by point_history_created_date desc";
		
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<PointHistory> pointHistories = new ArrayList<>();
		while (rs.next()) {
			PointHistory pointHistory = new PointHistory();
			pointHistory.setNo(rs.getInt("point_history_no"));
			pointHistory.setUserNo(userNo);
			pointHistory.setOrderNo(rs.getLong("order_no"));
			pointHistory.setHistoryReason(rs.getString("point_history_reason"));
			pointHistory.setAmount(rs.getInt("point_amount"));
			pointHistory.setCreatedDate(rs.getDate("point_history_created_date"));
			pointHistory.setUpdatedDate(rs.getDate("point_history_updated_date"));
			pointHistories.add(pointHistory);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return pointHistories;
	}
}
