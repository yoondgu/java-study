package quiz;

/*
 * 2. 특정 문자열을 앞으로 혹은 뒤로부터 읽어도 똑같은 문자인지 여부를 출력하시오.
 * 예) 오디오, 기러기 등
 */
public class Quiz2 {
	public static void main(String[] args) {
		phrasePalindrom("홍길동");
		phrasePalindrom("오디오");
		phrasePalindrom("기러기");
	}
	public static void phrasePalindrom(String word) {
		// 문자열을 앞으로 뒤로부터 읽어도 똑같은 문자면 true를 출력한다.
		// 길이가 3인 문자열이라면 첫글자, 마지막 글자만 비교하면 되지만 그 이상일 경우도 고려해야 한다.
		// 대칭 여부를 확인하는 것이므로 문자열의 중앙을 기준으로 절반만 검사하면 된다.
		
		final int len = word.length();
		boolean isSame = true;
		for (int i=0; i<len/2 ; i++) {
			int symmetricIndex = word.length()-(i+1);
			if (word.charAt(i) != word.charAt(symmetricIndex)) {
				isSame = false;
			}
		}
		System.out.println(word + ": " + isSame);
	}
}
