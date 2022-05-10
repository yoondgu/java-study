package quiz;

/*
 * 3. 암호문 만들기. 카이사르 암호는 가장 간단한 치환 암호방법으로 암호문을 만드는 것이다.
   알파벳을 일정거리를 두어 기존 문자열을 다른 문자로 치환하는 방법이다. 
   예를 들면 문자에 대해서 오른쪽으로 2칸 이동시키는 치환 암호방법으로 암호문을 만들면
   "ABC"는 "CDF"가 된다. 알파벳 대문자를 기준으로 카이사를 암호문을 작성하시오.
 */
public class Quiz3 {
	public static void main(String[] args) {
		rot13("ILOVEYOU");
	}
	public static void rot13(String text) {
		// 문자열을 오른쪽으로 13칸 이동시켜서 암호문을 작성하고 출력하시오.
		// 힌트1. 칸을 이동시킬 때는 문자열의 각 문자를 숫자로 변환해서 사용하는 것이 편리하다.
		// 힌트2. 알파벳 대문자는 정수로 변환했을 때 65 ~ 90까지의 범위를 가진다.
		// 힌트3. String 클래스의 char charAt(int index)는 문자열에서 지정된 index번째 문자를 char타입으로 반환한다.
		// 힌트4. char과 int는 상호형변환이 가능한 성질을 이용한다.
		// 힌트5. 문자를 숫자로 변환했을 때 77보다 큰 경우와 77이하인 경우를 구분해서 처리해야 한다.

		StringBuffer password = new StringBuffer();
		
		for (int i=0; i<text.length(); i++) {
			int target = text.charAt(i); // char와 int는 상호형변환이 가능하다. 정수 연산으로 문자의 위치를 이동시킨다.
			
			if (target < 65 || target > 90) {
				return;
			}
			
			int replacement = target > 77 ? target - 13 : target + 13;
			password.append((char)replacement);
		}
		System.out.println(password);
	}
}