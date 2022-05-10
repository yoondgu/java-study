package oop2;

/**
 * 책정보를 상속받아서 소설책정보(저자, 쟝르, 나라)를 정의하는 자식 클래스
 * @author doyoung
 *
 */
public class FictionBook extends Book {

	private String author;
	private String genre;
	private String country;
	
	public FictionBook() {}

	public FictionBook(int bookNo, String title, String publisher, int price, int discountPrice, String author,
			String genre, String country) {
		super(bookNo, title, publisher, price, discountPrice);
		this.author = author;
		this.genre = genre;
		this.country = country;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public void printBookInfo() {
		super.printBookInfo();
		System.out.println("저자: " + author);
		System.out.println("장르: " + genre);
		System.out.println("국가: " + country);
	}
}
