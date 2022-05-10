package oop1;
import java.util.ArrayList;

public class ScoreRepository {

	/*
	 * 시험정보를 여러 개 관리하는 객체를 정의하시오.
	 */
	public ScoreRepository() {
		saveScore(new Score("홍길동", 100, 100, 100));
		saveScore(new Score("김유신", 80, 70, 90));
		saveScore(new Score("강감찬", 95, 95, 100));
		saveScore(new Score("유관순", 50, 100, 100));
		saveScore(new Score("안중근", 100, 100, 100));
	}
	
	//	멤버변수에 시험정보를 여러개 저장할 수 있는 ArrayList객체를 생성해서 멤버변수로 참조하시오. ArrayList<Score> scoreList = new ArrayList<>();
	ArrayList<Score> scoreList = new ArrayList<>();
	
	// 시험정보를 전달받아서 멤버변수 ArrayList에 저장하는 멤버메소드를 정의하시오.
	public void saveScore(Score score) {
		scoreList.add(score);
	}
	
	// 모든 시험정보를 반환하는 멤버메소드를 정의하시오.
	public ArrayList<Score> getAllScore() {
		return scoreList;
	}
	
	// 시험정보 중에서 합격여부가 true로 판정된 시험정보들만 반환하는 멤버메소드를 정의하시오.
	public ArrayList<Score> getPassedScores() {
		ArrayList<Score> passedScores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.isPassed()) {
				passedScores.add(score);
			}
		}
		return passedScores;
	}
	
	// 시험정보 중에서 합격여부가 false로 판정된 시험정보들만 반환하는 멤버메소드를 정의하시오.
	public ArrayList<Score> getUnPassedScores() {
		ArrayList<Score> unPassedScores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.isPassed() == false) {
				unPassedScores.add(score);
			}
		}
		return unPassedScores;
	}
	
	public ArrayList<Score> getAbove95Scores() {
		ArrayList<Score> above95Scores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.getAverage() >= 95) {
				above95Scores.add(score);
			}
		}
		return above95Scores;
	}
	
	public void printSelectedScoreInfo(ArrayList<Score> scores) {
		System.out.println("[검색한 성적 정보 출력]");
		for (Score score : scores) {
			score.printScoreInfo();
			System.out.println();
		}
	}
}
