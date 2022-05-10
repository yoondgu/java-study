package app103;

public class OrderItemRepository {
	
	OrderItem[] db = new OrderItem[20];
	int savePosition = 0;
	
	/**
	 * 주문정보를 전달받아서 저장하는 기능
	 * @param item
	 */
	void saveOrderItem(OrderItem item) {
		db[savePosition] = item;
		savePosition ++;
	}
	
	/**
	 * 사용자이름을 전달받아서 해당 사용자가 등록한 주문내역을 반환하는 기능
	 * @param username
	 * @return OrderItem[] 타입의 해당 사용자의 주문내역 배열객체
	 */
	OrderItem[] getOrderItemsByUsername(String username) {
		OrderItem[] result = new OrderItem[10];
		int resultPosition = 0;
		for (int index = 0; index < savePosition; index++) {
			OrderItem foundCartItem = db[index];
			if (username.equals(foundCartItem.username)) {
				result[resultPosition] = foundCartItem;
				resultPosition++;
			}
		}
		return result;
	}
}
