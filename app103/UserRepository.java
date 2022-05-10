package app103;

public class UserRepository {
	
	User[] db = new User[10];
	int savePosition = 0;
	
	UserRepository() {
		db[savePosition++] = new User("홍길동");
		db[savePosition++] = new User("김유신");
		db[savePosition++] = new User("이순신");
		db[savePosition++] = new User("강감찬");
		db[savePosition++] = new User("류관순");
	}
	
	/**
	 * 새 사용자정보를 받아서 저장하는 기능
	 * @param user
	 * @return 저장이 완료되는 true를 반환한다. 이미 존재하는 사용자일 시 false를 반환한다.
	 */
	boolean saveUser(User user) {
		if(existUsername(user.name)) {
			return false;
		}
		db[savePosition] = user;
		savePosition++;
		return true;
	}
	
	/**
	 * 사용자명을 전달받아서 해당 사용자정보를 반환하는 기능
	 * @param nameforSearch
	 * @return User 타입의 사용자정보, 찾는 정보가 존재하지 않으면 null을 반환한다.
	 */
	User getUserByName(String nameforSearch) {
		for (int index = 0; index < savePosition; index++) {
			User savedUser = db[index];
			if (savedUser.name.equals(nameforSearch)) {
				return savedUser;
			}
		}
		return null;
	}
	
	/**
	 * 이름을 전달받아서 배열에 저장된 사용자중에서 같은 이름을 가진 사용자가 존재하는지 여부를 반환한다.
	 * @param name 사용자이름
	 * @return 존재여부, 존재하면 true를 반환한다.
	 */
	boolean existUsername(String name) {
		return getUserByName(name) != null ? true : false;
	}

}
