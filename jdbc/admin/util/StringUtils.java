package admin.util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * 숫자나 문자열을 다양한 포맷의 문자열로 변환해서 반환기능을 제공하는 클래스다.
 * @author doyoung
 *
 */
public class StringUtils {

	private static final DecimalFormat currencyFormat = new DecimalFormat("##,###");
	
	/**
	 * 정수를 전달받아서 ","가 포함된 금융통화형식의 문자열을 반환한다.
	 * @param value 정수
	 * @return 3자리마다 ","가 포함된 문자열 
	 */
	public static String numberToCurrency(int value) {
		return currencyFormat.format(value);
	}
	
	/**
	 * 정수를 전달받아서 ","가 포함된 금융통화형식의 문자열을 반환한다.
	 * @param value 정수
	 * @return 3자리마다 ","가 포함된 문자열 
	 */
	public static String numberToCurrency(long value) {
		return currencyFormat.format(value);
	}
	
	/**
	 * 객체가 null이면 빈 문자열을 반환한다.
	 * @param obj 객체
	 * @return 객체가 null이면 ""을 반환하고, 아니면 객체의 toString() 실행결과를 반환한다.
	 */
	public static String nullToBlank(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}
	
	/**
	 * 정수가 ","로 구분된 문자열을 받아서 Set&lt;Integer&gt;객체로 바꾸어 반환한다.
	 * @param text 문자열 "1234,1235,1236"
	 * @return ","로 구분한 정수를 담은 Set&lt;Integer&gt;객체
	 */
	public static Set<Integer> textToNumbers(String text) {
		Set<Integer> numbers = new HashSet<>();
		
		if (text == null) {
			return numbers;
		}
		if (text.isBlank()) {
			return numbers;
		}
		
		String[] items = text.split(",");
		for (String item : items) {
			try {
				int number = Integer.parseInt(item.trim()); // , 사이의 공백이 있을 경우 제거한다.
				numbers.add(number);
			} catch (NumberFormatException e) {} // "1234,1235,123t" 와 같이 변환불가한 문자열은 오류를 잡아서 제외하고 반복 재개
		}
		
		return numbers;
	}
		
}
