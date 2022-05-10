package oop2;


import java.util.ArrayList;

/**
 * Managable 인터페이스에 정의된 기능을 구현하고, 소설책, 잡지책 정보를 관리하는 클래스
 * @author doyoung
 *
 */
public class BookRepository implements Managable {
	
	private ArrayList<Book> books = new ArrayList<>();

	@Override
	public void saveBook(Book book) {
		books.add(book);
	}
	
	@Override
	public ArrayList<Book> getAllBook() {
		return books;
	}
	
	@Override
	public ArrayList<Book> getBooksByTitle(String keyword) {
		ArrayList<Book> booksByTitle = new ArrayList<>();
		for (Book book : books) {
			boolean hasKeyword = book.getTitle().contains(keyword);
			if (hasKeyword) {
				booksByTitle.add(book);
			}
		}
		return booksByTitle;
	}
	
	@Override
	public ArrayList<Book> getBooksByPrice(int min, int max) {
		ArrayList<Book> booksByPrice = new ArrayList<>();
		for (Book book : books) {
			boolean isWithinRange = (book.getPrice() >= min) && (book.getPrice() <= max);
			if (isWithinRange) {
				booksByPrice.add(book);
			}
		}
		return booksByPrice;
	}
}
