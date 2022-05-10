package app103;

public class CartItemRepository {
	
	CartItem[] db = new CartItem[20];
	int savePosition = 0;
	
	/**
	 * 장바구니 정보를 전달받아서 저장하는 기능
	 * @param item
	 * @return 저장 완료시 true를 반환하고, 이미 존재하는 상품일 경우 저장하지 않고 false를 반환한다.
	 */
	boolean saveCartItem(CartItem item) {
		CartItem savedCartItem = getCartItem(item.username, item.name);
		if (savedCartItem == null) {
			db[savePosition] = item;
			savePosition++;
			return true;
		}
		return false;
	}
	
	/**
	 * 사용자이름을 전달받아서 그 사용자가 등록한 카트정보를 반환하는 기능
	 * @param username
	 * @return CartItem[] 타입의 해당 사용자 카트정보를 반환한다. 카트정보가 없으면 빈 배열이 반환된다.
	 */
	CartItem[] getCartItemsByUsername(String username) {
		CartItem[] result = new CartItem[10];
		int resultPosition = 0;
		for (int index = 0; index < savePosition; index++) {
			CartItem foundCartItem = db[index];
			if (foundCartItem.username.equals(username)) {
				result[resultPosition] = foundCartItem;
				resultPosition++;
			}
		}
		return result;
	}
	
	/**
	 * 사용자 이름, 상품명을 전달받아서 사용자의 카트에서 해당 상품의 정보를 반환하는 기능
	 * @param username
	 * @return 해당 사용자가 장바구니에 담은 해당 상품명에 대한 카트 정보, 찾는 정보가 없을 시 null값을 반환한다.
	 */
	CartItem getCartItem(String username, String name) {
		for (int index = 0; index < savePosition; index++) {
			CartItem savedCartItem = db[index];
			if (savedCartItem.username.equals(username) && savedCartItem.name.equals(name)) {
				return savedCartItem;
			}
		}
		return null;
	}
	
	/**
	 * 사용자이름을 전달받아서 그 사용자가 등록한 모든 CartItem을 삭제하는 기능 
	 * @param username
	 */
	void removeCartItemsByUser(String username) {
		int[] savedIndexs = getIndexByUser(username);

		if (savedIndexs == null) {
			return;
		}
		for (int savedIndex : savedIndexs) {
			removeCartItemsByIndex(savedIndex);
		}
	}
	
	int[] getIndexByUser(String username) {
		int[] savedIndexs = new int[100];
		int savedPosition = 0;
		int savedIndex = -1;
		for (int index = 0; index <savePosition; index++) {
			CartItem cartItem = db[index];
			if (cartItem.username.equals(username)) {
				savedIndex = index;
				savedIndexs[savedPosition] = savedIndex;
				savedPosition++;
			}
		}
		return savedIndex != -1 ? savedIndexs : null;
	}
	
	void removeCartItemsByIndex(int removeIndex) {
		if (removeIndex == savePosition -1) {
			removeLastItem();
			return;
		}
		removeUnLastItem(removeIndex);
	}
	
	void removeLastItem() {
		db[savePosition -1] = null;
		savePosition--;
	}
	
	void removeUnLastItem(int removeIndex) {
		for (int index = removeIndex; index < savePosition; index++) {
			db[index] = db[index +1];
		}
		savePosition--;
	}
}
