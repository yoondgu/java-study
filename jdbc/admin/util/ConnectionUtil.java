package admin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	// static 초기화블록
	// 클래스가 메모리에 로딩될 때, 그 때 딱 한 번만 실행된다. (생성자 보다 먼저 실행)
	// 클래스의 일생에서 딱 한 번만 실행되는 것이다.
	// 실행할 때마다 드라이버 등록을 새로 할 필요 없도록 한다.
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
//			e.printStackTrace(); // 한 번 실행되는 막을 수 없는 오류이고, driver를 잘못 넣지는 않을 것.
			// 그래서 딱히 던져주는 게 큰 의미는 없다. 그래도 오류를 명기하려면 아래처럼 던진다.
			throw new RuntimeException("오라클 JDBC 드라이버를 찾을 수 없습니다.", e);
			// try/catch를 강제하지 않는 exception이다.
		}
	}
	
	// 상수 정의
	private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "hr";
	private static final String PASSWORD = "zxcv1234";
	
	/**
	 * 데이터베이스와 연결된 Connection 객체를 반환한다.
	 * @return Connection
	 * @throws SQLException 데이터베이스와 연결이 실패하면 이 예외를 던진다.
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL,USERNAME, PASSWORD);
	}
	
}
