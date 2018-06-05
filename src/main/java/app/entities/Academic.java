package app.entities;

import app.singletons.UserType;

public class Academic extends User {

	public Academic(User user) {
		super(user.getName(), user.getEmail(), user.getPassword(), user.getInstitution());
		super.setUserType(UserType.ACADEMIC);
	}

}