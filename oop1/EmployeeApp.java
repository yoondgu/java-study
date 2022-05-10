package oop1;
/**
 * 기본 생성자 메소드로 사원정보를 표현하는 객체 생성하기
 * 매개변수가 있는 생성자 메소드로 사원정보를 표현하는 객체 생성하기
 * @author doyoung
 *
 */
public class EmployeeApp {
	
	public static void main(String[] args) {
		
		Employee employee1 = new Employee();
		employee1.setNo(0);
		employee1.setName("홍길동");
		employee1.setEmail("hong@gmail.com");
		employee1.setTel("010-1111-1111");
		employee1.setDept("영업1팀");
		employee1.setPosition("대리");
		employee1.setSalary(2500000);
		employee1.setCommisionRate(0.02);
		employee1.setEntryDate("2021-03-02");
		employee1.setHasResignated(true);
		employee1.setResignDate("2022-03-18");
		employee1.setRegistryDate("2022-03-26");
		
		employee1.printEmployeeInfo();
		
		Employee employee2 = new Employee("김유신", "kim@naver.com", "010-2222-2222", "영업1팀", "팀장", 4000000, 0.02, "2022-03-26");

		employee2.printEmployeeInfo();
	}
	
}
