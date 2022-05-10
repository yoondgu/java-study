package oop3;

/*
 * 30
 * 20
 * 10
 * 위와 같은 실행결과를 얻도록 코드를 완성하시오.
 */
class Outer {
	int value = 10;
	// 외부클래스의 멤버변수
	
	class Inner {
		int value = 20;
		// 내부클래스의 멤버변수 
		
		void method() {
			int value = 30;
			// method()의 지역변수
			
			System.out.println(value);
			System.out.println(this.value);
			System.out.println(Outer.this.value);
		}
	}
}

class Exercise708 {
	
	public static void main(String[] args) {
		Outer outer = new Outer();
		Outer.Inner inner = outer.new Inner();
		
		inner.method();
	}

}
