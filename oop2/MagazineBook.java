package oop2;

/**
 * 책정보를 상속받아서 잡지책정보(카테고리, 부록, 발간일자)를 정의하는 자식 클래스
 */
public class MagazineBook extends Book {

	private String category;
	private Boolean hasAppendix;
	private String publicateDate;
	
	public MagazineBook() {}

	public MagazineBook(int bookNo, String title, String publisher, int price, int discountPrice, String category,
			Boolean hasAppendix, String publicateDate) {
		super(bookNo, title, publisher, price, discountPrice);
		this.category = category;
		this.hasAppendix = hasAppendix;
		this.publicateDate = publicateDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getHasAppendix() {
		return hasAppendix;
	}

	public void setHasAppendix(Boolean hasAppendix) {
		this.hasAppendix = hasAppendix;
	}

	public String getPublicateDate() {
		return publicateDate;
	}

	public void setPublicateDate(String publicateDate) {
		this.publicateDate = publicateDate;
	}
	
	@Override
	public void printBookInfo() {
		super.printBookInfo();
		System.out.println("카테고리: " + category);
		System.out.println("부록여부: " + hasAppendix);
		System.out.println("발간일: " + publicateDate);
	}
}
