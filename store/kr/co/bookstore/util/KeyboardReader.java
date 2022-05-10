package kr.co.bookstore.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 키보드 입력을 불린, 정수, 실수, 문자열로 읽어오는 readXXX()메소드를 제공하는 클래스다.
 * @author 중앙HTA
 *
 */
public class KeyboardReader {

	private static KeyboardReader instance = new KeyboardReader();
	public static KeyboardReader getInstance() {
		return instance;
	}	
	
	/**
	 * 키보드 입력을 한 줄단위로 읽어오는 BufferedReader객체가 대입되는 멤버변수다.
	 */
	private BufferedReader in;
	
	/**
	 * 키보드 입력을 한 줄단위로 읽어오는 BufferedReader객체를 생성해서 멤버변수 in에 대입한다.
	 */
	private KeyboardReader() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
		} catch (IOException e) {}
	}
	
	/**
	 * 키보드 입력을 읽어서 불린타입의 값을 반환한다.
	 * @return 불린값, true나 false가 아닌 문자열을 입력하면 false값이 반환된다.
	 */
	public boolean readBoolean() {
		try {
			return Boolean.parseBoolean(in.readLine().trim());
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 키보드 입력을 읽어서 정수로 반환합니다.
	 * @return 정수, 숫자가 아닌 값이 포함되어 있으면 0이 반환된다.
	 */
	public int readInt() {
		try {
			return Integer.parseInt(in.readLine().trim());
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 키보드 입력을 읽어서 long타입의 정수를 반환합니다.
	 * @return 정수, 숫자가 아닌 값이 포함되어 있으면 0L이 반환된다.
	 */
	public long readLong() {
		try {
			return Long.parseLong(in.readLine().trim());
		} catch (Exception e) {
			return 0L;
		}
	}
	
	/**
	 * 키보드 입력을 읽어서 소숫점이 포함된 실수를 반환합니다.
	 * @return 실수, 숫자가 아닌 값이 포함되어 있으면 0.0이 반환된다.
	 */
	public double readDouble() {
		try {
			return Double.parseDouble(in.readLine().trim());
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	/**
	 * 키보드 입력을 읽어서 문자열로 반환합니다.
	 * @return 문자열
	 */
	public String readString() {
		try {
			return in.readLine().trim();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 사용했던 리소스를 닫습니다.
	 */
	public void close() {
		try {
			in.close();
		} catch (IOException e) {}
	}
}
