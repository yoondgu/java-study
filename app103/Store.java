package app103;

public class Store {
	
	public static void main(String[] args) {
		KeyboardReader reader = new KeyboardReader();
		ProductRepository productRepo = new ProductRepository();
		UserRepository userRepo = new UserRepository();
		CartItemRepository cartItemRepo = new CartItemRepository();
		OrderItemRepository orderItemRepo = new OrderItemRepository();
		
		String borderLine = "-------------------------------------------------------------------------------------------";
		
		while (true) {
			System.out.println("\n\n");
			System.out.println("borderLine");
			System.out.println("1.사용자등록 2.상품조회 3.장바구니담기 4.구매하기 5.장바구니조회 6.구매내역조회 7.내정보 조회 8.장바구니주문 -1.종료");
			System.out.print("메뉴를 선택하세요: ");
			int menuNo = reader.readInt();
			
			if (menuNo == 1) {
				System.out.println(borderLine);
				System.out.println("<<사용자등록>>");
				System.out.print("이름을 입력하세요: ");
				String username = reader.readString();
				
				User user = new User(username);
				
				boolean isSaved = userRepo.saveUser(user);
				if (isSaved) {
					System.out.println("[완료] "+ user.name + "님 사용자 등록되었습니다.");
				} else {
					System.out.println("[오류] 이미 등록된 사용자입니다.");
				}
				
			} else if (menuNo == 2) {
				System.out.println(borderLine);
				System.out.println("<<상품조회>>");
				
				Product[] products = productRepo.getAllProducts();
				
				System.out.println();
				System.out.println("상품명\t제조사\t가격\t재고량\t판매여부\t");
				
				for(Product product: products) {
					System.out.print(product.name + "\t");
					System.out.print(product.maker + "\t");
					System.out.print(product.price + "\t");
					System.out.print(product.stock + "\t");
					System.out.println(product.onSell);
				}
				
			} else if (menuNo == 3) {
				System.out.println(borderLine);
				System.out.println("<<장바구니 담기>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				User user = userRepo.getUserByName(username);
				if(user == null) {
					System.out.println("[오류] 등록된 사용자정보가 없습니다.");
					continue;
				}
				
				System.out.print("장바구니에 담을 상품명을 입력하세요: ");
				String productName = reader.readString();
				
				Product product = productRepo.getProductByName(productName);
				if(product == null) {
					System.out.println("[오류] 등록된 상품정보가 없습니다.");
					continue;
				}
				
				CartItem item = new CartItem(username, product.name, product.price);
				
				boolean isSaved = cartItemRepo.saveCartItem(item);
				if (isSaved) {
					System.out.println("[완료] 장바구니에 상품이 추가되었습니다.");
				} else {
					System.out.println("[오류] 이미 장바구니에 담긴 상품입니다.");
				}
				
			} else if (menuNo == 4) {
				System.out.println(borderLine);
				System.out.println("<<구매하기>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				User user = userRepo.getUserByName(username);
				if (user == null) {
					System.out.println("[오류] 등록된 사용자정보가 없습니다.");
					continue;
				}
				
				System.out.print("구매할 상품명을 입력하세요: ");
				String productName = reader.readString();
				
				Product product = productRepo.getProductByName(productName);
				if (product == null) {
					System.out.println("[오류] 등록된 상품정보가 없습니다.");
					continue;
				}
				
				System.out.print("구매 수량을 입력하세요: ");
				int quantity = reader.readInt();
				
				if (quantity > product.stock) {
					System.out.println("[오류] 재고가 부족합니다.");
					continue;
				}

				OrderItem item = new OrderItem(username, product.name, product.price, quantity);
				orderItemRepo.saveOrderItem(item);
				product.stock -= quantity;
				
				System.out.println();
				System.out.println("상품 구매가 완료되었습니다.");
				
				user.point += (int)(item.orderPrice * 0.001); 
				System.out.println("현재 적립포인트는 [" + user.point + "] 점입니다.");
			
			} else if (menuNo == 5) {
				System.out.println(borderLine);
				System.out.println("<<장바구니조회>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				System.out.println();
				System.out.println("사용자\t상품명\t가격");
				
				CartItem[] cartItems = cartItemRepo.getCartItemsByUsername(username);
				for (CartItem item : cartItems) {
					if (cartItems[0] == null) {
						System.out.println("등록된 장바구니 내역이 상품이 없습니다.");
						break;
					} else if (item == null) {
						break;
					}
					System.out.print(item.username + "\t");
					System.out.print(item.name + "\t");
					System.out.println(item.price);
				}
				
			} else if (menuNo == 6) {
				System.out.println(borderLine);
				System.out.println("<<주문내역조회>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				System.out.println();
				System.out.println("상품명\t가격\t구매수량\t총구매금액");
				
				OrderItem[] orderItems = orderItemRepo.getOrderItemsByUsername(username);
				for (OrderItem item : orderItems) {
					if (orderItems[0] == null) {
						System.out.println("등록된 주문 내역이 없습니다.");
						break;
					} else if (item == null) {
						break;
					}
					System.out.print(item.name + "\t");
					System.out.print(item.price+ "\t");
					System.out.print(item.quantity+ "\t");
					System.out.println(item.orderPrice+ "\t");
				}
			
			} else if (menuNo == 7) {
				System.out.println(borderLine);
				System.out.println("<<내정보조회>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				User user = userRepo.getUserByName(username);
				
				if (user == null) {
					System.out.println("[오류] 사용자정보가 존재하지 않습니다.");
					continue;
				}
				
				System.out.println();
				System.out.println("사용자명\t포인트");
				System.out.print(user.name + "\t");
				System.out.println(user.point);
				
			} else if (menuNo == 8) {
				System.out.println(borderLine);
				System.out.println("<<장바구니주문>>");
				
				System.out.println();
				System.out.print("사용자명을 입력하세요: ");
				String username = reader.readString();
				
				User user = userRepo.getUserByName(username);
				if (user == null) {
					System.out.println("[오류] 사용자정보가 존재하지 않습니다.");
					continue;
				}
				
				CartItem[] cartItems = cartItemRepo.getCartItemsByUsername(username);
				int orderPrice = 0;
				for (CartItem item : cartItems) {
					if (cartItems[0] == null) {
						System.out.println("등록된 장바구니 정보가 없습니다.");
						break;
					}
					if (item == null) {
						break;
					}
					
					Product product = productRepo.getProductByName(item.name);
					System.out.print("[" + item.name + "]의 수량을 입력하세요: ");
					int quantity = reader.readInt();
					
					if (quantity > product.stock) {
						System.out.println("[오류] 재고가 부족합니다.");
						continue;
					}
					
					OrderItem orderItem = new OrderItem(item.username, item.name, item.price, quantity);
					orderItemRepo.saveOrderItem(orderItem);
					product.stock -= quantity;
					orderPrice += item.price;
				}
				
				System.out.println();
				System.out.println("[완료] 상품 구매가 완료되었습니다.");
				
				user.point += (int)(orderPrice * 0.001);  
				System.out.println("현재 적립포인트는 [" + user.point + "] 점입니다.");
				
				cartItemRepo.removeCartItemsByUser(username);
				System.out.println();
				System.out.println("[완료] 장바구니가 초기화되었습니다.");
				
			} else if (menuNo == -1) {
				System.out.println("[프로그램을 종료합니다.]");
				break;
			} else {
				System.out.print("[경고] 잘못된 번호입니다. 다시 입력하세요.");
			}
		}
		reader.close();
	}

}
