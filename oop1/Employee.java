package oop1;

import java.time.LocalDate;

/**
 * 사원정보를 표현하는 클래스
 * @author doyoung
 *
 */
public class Employee {

	private int no;
	private String name;
	private String email;
	private String tel;
	private String dept;
	private String position;
	private int salary;
	private double commisionRate;
	private String entryDate;
	private boolean hasResignated;
	private String resignDate;
	private String registryDate;
	
	public Employee() {}

	public Employee(String name, String email, String tel, String dept, int salary, String entryDate) {
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.dept = dept;
		this.salary = salary;
		this.entryDate = entryDate;
		position = "사원";
		commisionRate = 0.01;
		hasResignated = false;
		resignDate = "NotDefined";
		registryDate = LocalDate.now().toString();
		
	}

	public Employee(String name, String email, String tel, String dept, String position, int salary, double comRate,
			String entryDate) {
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.dept = dept;
		this.position = position;
		this.salary = salary;
		this.commisionRate = comRate;
		this.entryDate = entryDate;
		hasResignated = false;
		resignDate = "NotDefined";
		registryDate = LocalDate.now().toString();
		
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public double getCommisionRate() {
		return commisionRate;
	}

	public void setCommisionRate(double commisionRate) {
		this.commisionRate = commisionRate;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public boolean isHasResignated() {
		return hasResignated;
	}

	public void setHasResignated(boolean hasResignated) {
		this.hasResignated = hasResignated;
	}

	public String getResignDate() {
		return resignDate;
	}

	public void setResignDate(String resignDate) {
		if (hasResignated) {
			this.resignDate = resignDate;
		}
		this.resignDate = "NotDefined";
	}

	public String getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(String registryDate) {
		this.registryDate = registryDate;
	}
	
	public void printEmployeeInfo() {
		System.out.println("[사원정보 출력]");
		System.out.println("사원번호: " + no);
		System.out.println("사원이름: " + name);
		System.out.println("전자메일: " + email);
		System.out.println("전화번호: " + tel);
		System.out.println("소속부서: " + dept);
		System.out.println("직위: " + position);
		System.out.println("급여: " + salary);
		System.out.println("커미션비율: " + commisionRate);
		System.out.println("입사일: " + entryDate);
		System.out.println("퇴사여부: " + hasResignated);
		System.out.println("퇴사일: " + resignDate);
		System.out.println("등록일: " + registryDate);
	}
	
}
