package app103;

public class ProductRepository {
	
	Product[] db = new Product[10];
	
	public ProductRepository() {
		db[0] = new Product("볼펜 흑", "모나미", 1000);
		db[1] = new Product("볼펜 빨", "모나미", 1000);
		db[2] = new Product("볼펜 파", "모나미", 1000);
		db[3] = new Product("볼펜 초", "모나미", 1000);
		db[4] = new Product("볼펜 노", "모나미", 1000);
		db[5] = new Product("볼펜 3색", "모나미", 3000);
		db[6] = new Product("컴퓨터펜", "모나미", 800);
		db[7] = new Product("연필", "모닝글로리", 4000);
		db[8] = new Product("샤프", "모닝글로리", 1500);
		db[9] = new Product("지우개", "모닝글로리", 500);
	}
	
	/**
	 * 전체 상품정보를 반환하는 기능
	 * @return Product[] 타입의 데이터베이스
	 */
	Product[] getAllProducts() {
		return db;
	}
	
	/**
	 * 상품명을 전달받아 해당 상품 정보를 반환하는 기능
	 * @param selectName
	 * @return Product 타입의 상품정보 객체
	 */
	Product getProductByName(String selectName) {
		for (Product product : db) {
			if (product.name.equals(selectName)) {
				return product;
			}
	}
	return null;
	}
	
}
