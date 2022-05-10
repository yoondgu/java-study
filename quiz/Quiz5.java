package quiz;

import java.util.*;

/*
 * 5. List에 0부터 9사이의 숫자가 저장되어 있다. List에서 연속으로 같은 숫자가 나타나면 숫자를 하나만 남기고 전부 제거하여 출력한다.
 */
public class Quiz5 {
	
	public static void main(String[] args) {
		List<Integer> numbers = List.of(0, 9, 9, 9, 5, 5, 5,  4, 4, 3, 2, 2, 2, 1, 0, 0, 5, 5, 5, 5, 3);
		uniqueNumber(numbers);		// 출력결과 0, 9, 5, 4, 3, 2, 1, 0, 5, 3 이다.
	}
	
	public static void uniqueNumber(List<Integer> numbers) {
	
		int previousNumber = -1;
		
		for (int number : numbers) {
			if (previousNumber == number) {
				continue;
			}
			System.out.print(number + ", ");
			previousNumber = number;
		}
	}

}
	