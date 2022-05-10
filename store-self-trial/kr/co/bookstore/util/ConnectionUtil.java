package kr.co.bookstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 클래스 로딩 시 Oracle JDBC 드라이버를 JVM의 드라이버 레지스트리에 등록시킨다.
 * <p>
 * 데이터베이스와의 연결을 담당하는 Connection 객체를 제공하는 정적메소드를 제공한다.
 * @author doyoung
 *
 */
public class ConnectionUtil {

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "hr";
	private static final String PASSWORD = "zxcv1234";
	
	/**
	 * 데이터베이스와의 연결을 담당하는 Connection 객체를 반환한다.
	 * @return Connection 객체
	 * @throws SQLException 데이터베이스 액세스 작업 중 오류가 발생하면 이 예외를 던진다.
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
}
