package oop1;
import java.util.ArrayList;

/**
 * 시험정보를 여러 개 관리하는 클래스
 * @author doyoung
 *
 */
public class ScoreRepository0 {

	private ArrayList<Score> scoreList = new ArrayList<>();
	
	public void saveScore(Score score) {
		scoreList.add(score);
	}
	
	public ArrayList<Score> getAllScore() {
		return scoreList;
	}
	
	public ArrayList<Score> getPassedScores() {
		ArrayList<Score> passedScores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.isPassed()) {
				passedScores.add(score);
			}
		}
		return passedScores;
	}
	
	public ArrayList<Score> getUnpassedScores() {
		ArrayList<Score> unPassedScores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.isPassed() == false) {
				unPassedScores.add(score);
			}
		}
		return unPassedScores;
	}

	public ArrayList<Score> getAbove95scores() {
		ArrayList<Score> above95Scores = new ArrayList<>();
		for (Score score : scoreList) {
			if (score.getAverage() >= 95) {
				above95Scores.add(score);
			}
		}
		return above95Scores;
	}

}
