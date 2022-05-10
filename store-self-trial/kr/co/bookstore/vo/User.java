package kr.co.bookstore.vo;

import java.util.Date;

public class User {

	private int no;
	private String email;
	private String password;
	private String name;
	private String tel;
	private int point;
	private String deleted;
	private Date createdDate;
	private Date updatedDate;
	
	public User() {}
	
	public User(int no, String email, String password, String name, String tel) {
		this.no = no;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

	@Override
	public String toString() {
		return "User [no=" + no + ", email=" + email + ", password=" + password + ", name=" + name + ", tel=" + tel
				+ ", point=" + point + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}
	
}
