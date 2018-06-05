package app.entities;

import app.singletons.UserType;

public class Author extends Academic {
	
	public Author(User user, Media media) {
		super(user);
		super.setUserType(UserType.AUTHOR);
	}
	
}