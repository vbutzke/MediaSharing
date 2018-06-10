package app.entities;

import java.util.HashMap;
import java.util.InputMismatchException;
import javax.mail.MessagingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import app.singletons.Email;
import database.DatabaseController;

public class Profile {
	
	private User user;
	private EmailJob emailJob;
	private DatabaseController dbController;
	
	public Profile(User user, DatabaseController db) {
		this.user 		= user;
		this.dbController = db;
	}
	
	public void addEmail(String email) throws MessagingException, JsonProcessingException {
		user.addEmail(email);
		emailJob.sendEmail(Email.CONFIRMATION_EMAIL);
		dbController.addEmail(user, email);
	}
	
	public void removeEmail(int key) throws JsonProcessingException {
		user.removeEmail(key);
		dbController.removeEmail(user, key);
	}
	
	public void changeToAuthor() throws JsonProcessingException {
		user = (User) dbController.changeToAuthor(user).toEntity();
	}
	
	public void addInstitution(Institution institution) throws JsonProcessingException {
		user.addInstitution(institution);
		dbController.addInstitutionToUser(user, institution);
	}
	
	public void removeInstitution(int key) throws JsonProcessingException {
		user.removeInstitution(key);
		dbController.removeInstitutionFromUser(user, key);
	}
	
	public void changePassword(String newPassword, String passwordConfirmation) throws InputMismatchException, MessagingException, JsonProcessingException {
		
		if(newPassword.equals(passwordConfirmation)) {
			user.setPassword(newPassword);
			dbController.changePassword(user, newPassword);
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
		dbController.removeAccount(user);
	}
	
	public void changeName(String name) throws JsonProcessingException {
		user.setName(name);
		dbController.changeName(user, name);
	}

	public User getUser() {
		return user;
	}
	
}