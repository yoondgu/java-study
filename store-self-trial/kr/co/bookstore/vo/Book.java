package kr.co.bookstore.vo;

import java.util.Date;

public class Book {

	private int no;
	private String title;
	private String author;
	private String publisher;
	private String description;
	private int price;
	private int discountPrice;
	private String onSell;
	private int stock;
	private Date createdDate;
	private Date upatedDate;
	
	public Book() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getOnSell() {
		return onSell;
	}

	public void setOnSell(String onSell) {
		this.onSell = onSell;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpatedDate() {
		return upatedDate;
	}

	public void setUpatedDate(Date upatedDate) {
		this.upatedDate = upatedDate;
	}
	
}
