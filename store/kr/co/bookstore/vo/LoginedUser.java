package kr.co.bookstore.vo;

import java.util.Date;

public class LoginedUser {

	private long sessionId;
	private int userNo;
	private Date lastLoginDate;
	private String status;
	
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
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
