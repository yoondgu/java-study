package oop1;

/**
 * 시험정보(응시자명, 국어점수, 영어점수, 수학점수, 평균, 합격여부, 과락여부)를 표현하는 클래스
 * @author doyoung
 *
 */
public class Score {

	private String studentName;
	private int kor;
	private int eng;
	private int math;
	
	public Score() {}

	public Score(String studentName, int kor, int eng, int math) {
		this.studentName = studentName;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}

	public String getStudentName() {
		return studentName;
	}

	public int getKor() {
		return kor;
	}

	public int getEng() {
		return eng;
	}

	public int getMath() {
		return math;
	}
	
	public int getAverage() {
		return (kor + eng + math)/3;
	}
	
	public boolean isPassed() {
		if (isFailedAtSubject()) {
			return false;
		}
		if (getAverage() < 60) {
			return false;
		}
		return true;
	}
	
	public boolean isFailedAtSubject() {
		if (kor < 40 || eng < 40 || math <40) {
			return true;
		}
		return false;
	}
	
	public void printScoreInfo() {
		System.out.println("학생이름: " + studentName);
		System.out.println("국어점수: " + kor);
		System.out.println("영어점수: " + eng);
		System.out.println("수학점수: " + math);
		System.out.println("평균점수: " + getAverage());
		System.out.println("합격여부: " + isPassed());
		System.out.println("과락여부: " + isFailedAtSubject());
	}
	
}
