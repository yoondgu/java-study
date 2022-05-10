package quiz;

/*
 * 4. 단어에서 가운데 글자를 출력하기. 단어의 길이가 홀수면 한글자, 단어의 길이가 짝수라면 가운데 2글자를 출력해야 한다.
 */
public class Quiz4 {
	public static void main(String[] args) {
		middleCharacter("서울특별시");
		middleCharacter("중앙HTA학원");
		middleCharacter("대용량데이터베이스솔루션");
	}
	public static void middleCharacter(String word) {
		// 단어의 가운데 글자를 출력하기
		final int len = word.length();
		String value = "";
		
		if (word.length()%2 == 0) {
			int begin = len/2 - 1;
			int end = len/2 + 1;
			value = word.substring(begin, end);
		} else {
			int middle = (int)Math.floor(len/2);
			value = String.valueOf(word.charAt(middle));
		}
		
		System.out.println(value);
	}
}
