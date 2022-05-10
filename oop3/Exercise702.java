package oop3;
/*
 * 섯다카드 20장을 포함하는 섯다카드 한 벌(SutdaDeck 클래스)을 정의한 것이다. 섯다카드 20장을 담는 SutdaCard배열을 초기화하시오.
 * 단 섯다카드는 1부터 10까지의 숫자가 적힌 카드가 한쌍씩 있고, 숫자가 1,3,8인 경우에는 둘 중의 한 장은 광(Kwang)이어야 한다. 
 * 즉, StudaCard의 인스턴스 변수 isKwang의 값이 true이어야 한다.
 */
class SutdaDeck {
	final int CARD_NUM = 20;
	SutdaCard[] cards = new SutdaCard[CARD_NUM];
	
	// (1) 배열 SutdaCard를 적절히 초기화하시오.
	SutdaDeck() {
			for (int i=0; i<cards.length; i++) {
				int num = i%10+1;
				boolean isKwang = (i < 10) && (num==1 || num==3 || num==8);
				cards[i] = new SutdaCard(num,isKwang);
			}
//		for (int i=1; i<=10; i++) {
//			boolean isKwang = i==1 || i==3 || i==8;
//			cards[i-1] = new SutdaCard(i,isKwang);
//		}
//		for (int i=11; i<=20; i++) {
//			cards[i-1] = new SutdaCard(i-10, false);
//		}
	}
	
	// (2) 연습문제7-1의 SutdaDeck 클래스에 다음에 정의된 새로운 메서드를 추가하고 테스트하시오. 
	// 1. shuffle() 배열cards에 담긴 카드의 위치를 뒤섞는다. Math.random() 사용
	// 2. pick(index) 배열 cards에서 지정된 위치의 SutdaCard를 반환
	// 3. pick() 배열 cards에서 임의의 위치의 SutdaCard를 반환 Math.random() 사용
	void shuffle() {
		// 카드의 인덱스 값은 0~19의 랜덤한 값이다. 임의의 위치의 카드와 서로 맞바꾸는 작업을 for문으로 돌린다.
		for (int i=0; i<cards.length; i++) {
			int index = (int)((Math.random())*cards.length);
			// cards[i]에 담겨있던 카드객체를 shuffleCard 변수에 담아두고, cards[i]에 cards[index]의 카드객체를 담는다.
			// 그리고 cards[index]에 shuffleCard에 담아둔 기존의 cards[i]의 카드객체를 담는다.
			SutdaCard shuffleCard = cards[i]; // 배열 cards의 i번째에 담긴 (1)'SutdaCard객체의 참조값'이 변수 shuffleCard에 저장된다.
			cards[i] = cards[index]; // 배열 cards의 i번째 칸에 index번째에 담겨있는 (2)'SutdaCard객체의 참조값'이 새로 담긴다.
			cards[index] = shuffleCard; // 배열 cards의 index번째 칸에 shuffleCard에 담겨있는 (1)'SutdaCard객체의 참조값'이 저장된다.
		}
	}
	
	SutdaCard pick(int index) {
		if (index < 0 || index >= cards.length) {
			return null;
		}
		return cards[index];
	}
	
	SutdaCard pick() {
		int index = (int)((Math.random())*cards.length);
		return cards[index];
	}
	
}

class SutdaCard {
	int num;
	boolean isKwang;
	
	public SutdaCard() {
		this(1,true);
	}

	public SutdaCard(int num, boolean isKwang) {
		this.num = num;
		this.isKwang = isKwang;
	}
	
	@Override
	public String toString() {
		return num + (isKwang ? "K" : "");
	}
}

class Exercise702 {
	public static void main(String[] args) {
		
		SutdaDeck deck = new SutdaDeck();
		SutdaCard[] cards = deck.cards;
		
		for (SutdaCard card : cards) {
			System.out.print(card + ",");
		}
		
		System.out.println();
		System.out.println(deck.pick(0));
		System.out.println(deck.pick());
			
		deck.shuffle();
			
		for (SutdaCard card : deck.cards) {
			System.out.print(card + ",");
		}
		
		System.out.println();
		System.out.println(deck.pick(0));
		System.out.println(deck.pick());
	}
}