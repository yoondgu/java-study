package kr.co.bookstore.vo;

import java.util.Date;

public class PointHistory {

	private int no;
	private int userNo;
	// order_no가 null이 허용되는 컬럼인데 기본자료형이라면 테이블에 null 대신 0 값이 들어가서 FK제약조건 위배되므로, 그런 예외를 처리해주거나 wrapper 타입으로 정의할 것
	private int orderNo;
	private String reason;
	private int amount;
	private Date createdDate;
	private Date updatedDate;
	
	public PointHistory() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
