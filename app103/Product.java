package app103;

public class Product {
	
	String name;
	String maker;
	int price;
	int stock;
	boolean onSell;
	
	public Product(String name, String maker, int price) {
		this.name = name;
		this.maker = maker;
		this.price = price;
		this.stock = 10;
		this.onSell = true;
	}

}
