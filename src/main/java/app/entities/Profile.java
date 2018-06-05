package app.entities;

import java.util.HashMap;
import java.util.InputMismatchException;
import javax.mail.MessagingException;
import app.singletons.Email;
import database.DatabaseBoundary;

public class Profile {
	
	private User user;
	private EmailJob emailJob;
	private DatabaseBoundary dbBoundary;
	
	public Profile(User user, DatabaseBoundary db) {
		this.user 		= user;
		this.dbBoundary = db;
	}
	
	public void addEmail(String email) throws MessagingException {
		user.addEmail(email);
		emailJob.sendEmail(Email.CONFIRMATION_EMAIL);
	}
	
	public void removeEmail(int key) {
		user.removeEmail(key);
	}
	
	public void addInstitution(Institution institution) {
		user.addInstitution(institution);
	}
	
	public void removeInstitution(int key) {
		user.removeInstitution(key);
	}
	
	public void changePassword(String newPassword, String passwordConfirmation) throws InputMismatchException, MessagingException {
		
		if(newPassword.equals(passwordConfirmation)) {
			user.setPassword(newPassword);
			HashMap<Integer, String> emails = user.getEmail();
			
			for(int i=0; i<emails.size(); i++) {
				Email.RESET_PASSWORD_EMAIL.setTo(emails.get(i));
				emailJob.sendEmail(Email.RESET_PASSWORD_EMAIL);
			}
		} else {
			throw new InputMismatchException("Password and password confirmation fields do not match. Please confirm you've typed it correctly.");
		}
		
		Email.RESET_PASSWORD_EMAIL.setTo("");
		
	}
	
	public void removeAccount() {
		dbBoundary.removeAccount();
	}
	
	public void changeName(String name) {
		user.setName(name);
	}

	public User getUser() {
		return user;
	}
	
}