package app103;

public class User {
	
	String name;	// 사용자명
	int point;		// 구매적립포인트, 구매금액의 0.1%를 적립시킨다.
	
	public User(String name) {
		this.name = name;
		this.point = 10;
	}

}
