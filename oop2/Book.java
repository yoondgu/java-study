package oop2;

/**
 * 책정보(번호, 제목, 출판사, 가격, 할인가격)를 표현하는 부모클래스
 * @author doyoung
 *
 */
public class Book {
	private int bookNo;
	private String title;
	private String publisher;
	private int price;
	private int discountPrice;
	
	public Book() { }

	public Book(int bookNo, String title, String publisher, int price, int discountPrice) {
		this.bookNo = bookNo;
		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.discountPrice = discountPrice;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public void printBookInfo() {
		System.out.println("[도서 정보]");
		System.out.println("번호: " + bookNo);
		System.out.println("제목: " + title);
		System.out.println("출판사: " + publisher);
		System.out.println("가격: " + price);
		System.out.println("할인가격: " + discountPrice);
	}
}
