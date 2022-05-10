package app103;

public class CartItem {
	
	String username;	// 사용자명
	String name;		// 상품명
	int price;			// 상품가격
	
	public CartItem(String username, String name, int price) {
		this.username = username;
		this.name = name;
		this.price = price;
	}
	
}
