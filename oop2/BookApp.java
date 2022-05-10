package oop2;

/**
 * 소설정보 객체, 잡지정보 객체를 생성하고, 멤버변수를 초기화하기
 * @author doyoung
 *
 */
public class BookApp {

	public static void main(String[] args) {
		
		FictionBook fiction = new FictionBook();
		
		fiction.setBookNo(00);
		fiction.setTitle("야간 경비원의 일기");
		fiction.setPublisher("현대문학");
		fiction.setPrice(11200);
		fiction.setDiscountPrice(10080);
		fiction.setAuthor("정지돈");
		fiction.setGenre("소설");
		fiction.setCountry("한국");
		
		MagazineBook magazine = new MagazineBook();
		
		magazine.setBookNo(1);
		magazine.setTitle("한겨레21");
		magazine.setPublisher("한겨레신문사");
		magazine.setPrice(4000);
		magazine.setDiscountPrice(3800);
		magazine.setCategory("주간지");
		magazine.setHasAppendix(false);
		magazine.setPublicateDate("2022-03-10");
	}
}
