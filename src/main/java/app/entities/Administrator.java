package app.entities;

import app.RandomGenerator;
import app.singletons.UserType;

public class Administrator extends Academic {
	
	public Administrator(User user) {
		super(user);
		super.setUserType(UserType.ADMINISTRATOR);
	}

	public String generateAccessCode() {
		RandomGenerator rg = new RandomGenerator();
		return rg.generateAlphaNumericCode(3, 1000, 9999);
	}
	
}