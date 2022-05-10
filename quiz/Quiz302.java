package quiz;

/*
 * 3. 암호문 만들기. 카이사르 암호는 가장 간단한 치환 암호방법으로 암호문을 만드는 것이다.
   알파벳을 일정거리를 두어 기존 문자열을 다른 문자로 치환하는 방법이다. 
   예를 들면 문자에 대해서 오른쪽으로 2칸 이동시키는 치환 암호방법으로 암호문을 만들면
   "ABC"는 "CDF"가 된다. 알파벳 대소문자를 기준으로 카이사를 암호문을 작성하시오.
 */
public class Quiz302 {
	public static void main(String[] args) {
		rot("I love you",3);
	}
	public static void rot(String text, int shift) {

		final int len = text.length(); // 생성된 객체 내에서는 상수처럼 쓰인다.
		final int aToZLength = 'z' - 'A' + 1; 
		// 문자에 대한 정수 연산값이 대소문자 알파벳의 범위를 넘어가는 경우를 고려하기 위함임. 
		
		StringBuilder password = new StringBuilder();
		
		for (int i=0; i<len; i++) {
			char ch = text.charAt(i); 
			
			if (ch == ' ') {
				password.append(ch);
				continue;
			}
			
			int value = ch + shift > 'z' ? ch + shift - aToZLength : ch + shift;
			password.append((char)value);
		}
		System.out.println(password);
	}
}