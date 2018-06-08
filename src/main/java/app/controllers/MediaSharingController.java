package app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import javax.mail.MessagingException;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import app.entities.*;
import app.singletons.*;

@Controller
public class MediaSharingController {

	private Dashboard dashboard;
	private AuthenticationController auth;
	
	public MediaSharingController(Dashboard dashboard, AuthenticationController auth) {
		this.dashboard = dashboard;
		this.auth = auth;
	}
	
	public Profile accessProfile() {
		return dashboard.accessProfile();
	}
	
	public void changePassword(String newPassword, String confirmation) throws InputMismatchException, MessagingException {
		dashboard.accessProfile().changePassword(newPassword, confirmation);
	}
	
	public void addEmail(String email) throws MessagingException {
		dashboard.accessProfile().addEmail(email);
	}
	
	public void removeEmail(int key) {
		dashboard.accessProfile().removeEmail(key);
	}
	
	public void deleteAccount() {
		dashboard.accessProfile().removeAccount();
	}
	
	public String generateAccessCode() throws SecurityException, IOException {
		User user = dashboard.accessProfile().getUser();
		String accessCode = "";
		
		if(user.getUserType() == UserType.ADMINISTRATOR) {
			Administrator admin = (Administrator)user;
			accessCode = admin.generateAccessCode();
			dashboard.addAccessCode(accessCode);
		} else {
			throw new SecurityException(Exceptions.ACCESS_CODE_TRIGGERING_VIOLATION.getMessage());
		}
		
		return accessCode;
	}
	
	public void changeName(String name) {
		dashboard.accessProfile().getUser().setName(name);
	}
	
	public void addInstitution(Institution institution) {
		dashboard.accessProfile().getUser().addInstitution(institution);
	}
	
	public void removeInstitution(int key) {
		dashboard.accessProfile().getUser().removeInstitution(key);
	}
	
	public Dashboard accessDashboard() {
		return dashboard;
	}
	
	public HashMap<Integer, Media> searchInAllMedias(String name, String description, LinkedList<Author> authors, MediaType type) {
		return dashboard.accessAllMedias().searchMedia(name, description, authors, type);
	}
	
	public void addMedia(Media media) {
		dashboard.addMedia(media);
	}
	
	public Medias accessMyMedia() {
		return dashboard.accessMyMedias();
	}
	
	public HashMap<Integer, Media> searchOnMyMedias(String name, String description, LinkedList<Author> authors, MediaType type) {
		return dashboard.accessMyMedias().searchMedia(name, description, authors, type);
	}
	
	public Medias accessFavorites() throws JsonProcessingException {
		return dashboard.accessFavorites();
	}
	
	public HashMap<Integer, Media> searchOnFavorites(String name, String description, LinkedList<Author> authors, MediaType type) throws JsonProcessingException {
		 return dashboard.accessFavorites().searchMedia(name, description, authors, type);
	}
	
	public Media clickOnMedia(int key) {
		return dashboard.accessAllMedias().getMedia(key);
	}
	
	public void deleteMedia(int key) {
		dashboard.removeMedia(key);
	}
	
	public void addToFavorites(Media media) throws JsonProcessingException {
		dashboard.accessFavorites().addMedia(media);
	}
	
	public void removeFromFavorites(int key) throws JsonProcessingException {
		dashboard.accessFavorites().removeMedia(key);
	}
	
	public void editMedia(int key, Media media) {
		dashboard.editMedia(key, media);
	}
	
	public Medias accessAllMedias() {
		return dashboard.accessAllMedias();
	}
	
	public void createInstitution(Institution institution) throws JsonProcessingException {
		dashboard.addInstitution(institution);
	}
	
	public void deleteInstitution(int id) {
		dashboard.deleteInstitution(id);
	}
	
	public void logout() {
		dashboard = null;
		auth.logout();
		auth = null;
	}
	
}