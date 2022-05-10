package oop1;
import java.util.ArrayList;

public class ScoreClient {

	public static void main(String[] args) {
		
		ScoreRepository scoreRepository = new ScoreRepository();
		
		ArrayList<Score> scores = scoreRepository.getAllScore();
		ArrayList<Score> scoresPassed = scoreRepository.getPassedScores();
		ArrayList<Score> scoresUnPassed = scoreRepository.getUnPassedScores();
		ArrayList<Score> scoresAbove95 = scoreRepository.getAbove95Scores();
		
		scoreRepository.printSelectedScoreInfo(scores);
		scoreRepository.printSelectedScoreInfo(scoresPassed);
		scoreRepository.printSelectedScoreInfo(scoresUnPassed);
		scoreRepository.printSelectedScoreInfo(scoresAbove95);
		
		
	}
}
