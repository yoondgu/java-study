package oop2;

import java.util.ArrayList;

public class BookClient {
	
	public static void printResult(ArrayList<Book> books) {
		for (Book book : books) {
			book.printBookInfo();
		}
	}

	public static void main(String[] args) {
		BookRepository rp = new BookRepository();
		Book book1 = new FictionBook(0, "한국어소설", "현대문학", 1000, 800, "작가", "소설", "한국");
		Book book2 = new MagazineBook(1, "수학잡지", "학회", 2000, 1300, "수학", false, "2022-03-26");
		Book book3 = new Book(2, "영어책", "출판사", 4000, 700);
		
		
		rp.saveBook(book1);
		rp.saveBook(book2);
		rp.saveBook(book3);
		
		ArrayList<Book> books = rp.getAllBook();
		ArrayList<Book> booksByTitle = rp.getBooksByTitle("국어");
		ArrayList<Book> booksByPrice = rp.getBooksByPrice(1200, 3000);
		
		System.out.println("[전체 도서정보 조회]");
		printResult(books);
		System.out.println();
		
		System.out.println("[키워드검색 조회]");
		printResult(booksByTitle);
		System.out.println();
		
		System.out.println("[가격검색 조회]");
		printResult(booksByPrice);
		
	}
}
