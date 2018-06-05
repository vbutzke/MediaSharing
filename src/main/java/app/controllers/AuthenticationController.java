package app.controllers;

import java.security.InvalidParameterException;
import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;
import org.springframework.stereotype.Controller;
import app.entities.Academic;
import app.entities.Administrator;
import app.entities.Dashboard;
import app.entities.EmailJob;
import app.entities.Institution;
import app.entities.User;
import app.singletons.Email;
import app.singletons.Exceptions;

@Controller
public class AuthenticationController {
	
	private EmailJob emailJob;
	
	public AuthenticationController() {
		emailJob = new EmailJob();
	}
	
	public Dashboard login(String email, String password) throws LoginException {
		
		boolean isEmailValid 	= false;
		boolean isPasswordValid = false;
		
		if(isEmailValid && isPasswordValid) {
			Dashboard d = new Dashboard(email);
			return d;
			
		} else if(isEmailValid && !isPasswordValid) {
			throw new LoginException(Exceptions.PASSWORD_MISMATCH.getMessage());
		} else if(!isEmailValid) {
			throw new LoginException(Exceptions.EMAIL_NOT_REGISTERED.getMessage());
		}
		
		return null;
		
	}
	
	public void createUser(String name, String lastName, String email, String password, String confirmation, String accessCode) throws InvalidParameterException{
		
		if(isInformationFilled(name, lastName, email, password, confirmation, accessCode)) {
			String status = isInformationValid(email, password, confirmation);
			if(status.equals("Ok")) {
				String fullName = name+" "+lastName;
				User user = new User(fullName, email, password, getInstitution(getEmailProvider(email)));
				
				if(isAdministrator(accessCode)) {
					Administrator admin = new Administrator(user);
					//grava as coisas no banco
				} else {
					Academic academic = new Academic(user);
					//grava as coisas no banco
				}
				
			} else {
				throw new InvalidParameterException(status);
			}
			
		} else {
			throw new InvalidParameterException(Exceptions.MISSING_MANDATORY_FIELDS.getMessage());
		}
		
	}
	
	public void resetPassword() throws MessagingException {
		emailJob.sendEmail(Email.RESET_PASSWORD_EMAIL);
	}
	
	public void logout() {
		
	}
	
	private boolean isInformationFilled(String name, String lastName, String email, String password, String confirmation, String accessCode) {
		if(isInformationFilled(name)  && isInformationFilled(lastName) &&
		   isInformationFilled(email) && isInformationFilled(password) &&
		   isInformationFilled(confirmation)) {
					return true;
		} 
		return false;
	}
	
	private boolean isInformationFilled(String value) {
		if(value.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private String isInformationValid(String email, String password, String confirmation) {
		if(isEmailValid(email) ){
			if(isPasswordValid(password, confirmation)) {
				return "Ok";
			}
			return "Password and password confirmation are mismatching, please review.";
		}
		return "Email has invalid format, please review";
	}
	
	private boolean isEmailValid(String email) {
		if(email.contains("@")) {
			String suffix = email.substring(email.indexOf("@"));
			String emailProvider = suffix.substring(0, suffix.indexOf("."));
			String domain = suffix.substring(suffix.indexOf("."));
			
			if(!emailProvider.isEmpty() && !domain.isEmpty() && !domain.contains("@")) {
				return true;
			}
		}
		
		return false;
	}
	
	private String getEmailProvider(String email) {
		String suffix = email.substring(email.indexOf("@"));
		return suffix.substring(0, suffix.indexOf("."));
	}
	
	private boolean isPasswordValid(String password, String confirmation) {
		if(password.equals(confirmation)) {
			return true;
		}
		return false;
	}
	
	private boolean isAccessCodeValid(String accessCode) {
		//check on record
		
		return false;
	}
	
	private boolean isAdministrator(String accessCode) throws InvalidParameterException{
		//need to persist access codes
		if(isInformationFilled(accessCode)) {
			if(isAccessCodeValid(accessCode)) {
				return true;
			} else {
				throw new InvalidParameterException(Exceptions.INVALID_ACCESS_CODE.getMessage());
			}
		}
		
		return false;
		
	}
	
	private Institution getInstitution(String emailProvider) {
		
		Institution i;
		return i;
		
	}
}
