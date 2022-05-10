package oop2;


import java.util.ArrayList;

/**
 * 소설책, 잡지책 정보를 관리하는 객체가 반드시 구현할 기능을 정의한 인터페이스
 * @author doyoung
 *
 */
public interface Managable {

	void saveBook(Book book);
	ArrayList<Book> getAllBook();
	ArrayList<Book> getBooksByTitle(String keyword);
	ArrayList<Book> getBooksByPrice(int min, int max);
}
