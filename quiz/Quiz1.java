package quiz;

/*
 * 1. 나열된 숫자에서 특정 조건에 부합하는 숫자가 나타날 경우 Fizz 또는 Buzz를 출력한다.
 * 3의 배수면 Fizz, 5의 배수면 Buzz, 3과 5의 배수면 PizzBuzz를 출력하시오.
 */
public class Quiz1 {
	public static void main(String[] args) {
		int[] numbers = new int[30];
		int number = 0;
		for (int i=0; i<30; i++) {
			number++;
			numbers[i] = number; 
		}
		fizzBuzz(numbers);
	}
	public static void fizzBuzz(int[] numbers) {
		// TODO 3의 배수면 Fizz, 5의 배수면 Buzz, 3과 5의 배수면 PizzBuzz를 출력하시오.
		for (int number : numbers) {
			if (number%15 == 0) {
				System.out.println(number + " PizzBuzz");
			} else if (number%3 == 0) {
				System.out.print(number + " Fizz");
			} else if (number%5 == 0) {
				System.out.println(number + " Buzz");
			}
		}
	}
}
