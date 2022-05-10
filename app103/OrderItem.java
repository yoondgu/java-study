package app103;

public class OrderItem {
	
	String username;	// 사용자명
	String name;		// 상품명
	int price;			// 상품가격
	int quantity;		// 구매수량
	int orderPrice;		// 총구매가격
	
	public OrderItem(String username, String name, int price, int quantity) {
		this.username = username;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.orderPrice = price*quantity;
	}
}
