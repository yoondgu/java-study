package kr.co.bookstore.vo;

import java.util.Date;

public class LoginedUser {

	private long sessionId;
	private int userNo;				// User의 no를 참조할 것이므로 이렇게 쓰는 게 좋다.
	private Date lastLoginDate;
	private String Status;
	
	public LoginedUser() {}
	
	public LoginedUser(long sessionId, int userNo) {
		this.sessionId = sessionId;
		this.userNo = userNo;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}
