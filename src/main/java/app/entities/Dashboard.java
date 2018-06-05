package app.entities;

import java.util.HashMap;
import java.util.LinkedList;
import app.singletons.MediaType;
import database.DatabaseBoundary;

public class Dashboard {

	private Profile profile;
	private MyMedias myMedias;
	private Favorites favorites;
	private AllMedias allMedias;
	private DatabaseBoundary db;
	private LinkedList<Author> author;
	
	public Dashboard(String email) {
		this.db 	   = new DatabaseBoundary();
		this.profile   = new Profile(db.getUser(email), db);
		this.myMedias  = db.getMyMedias(email);
		this.favorites = db.getFavorites(email);
		this.allMedias = db.getAllMedias();	
		this.author.add((Author)profile.getUser());
	}
	
	public HashMap<Integer, Media> searchMedia(String name, String description, LinkedList<Author> authors, MediaType mediaType) {
		return allMedias.searchMedia(name, description, authors, mediaType);
	}
	
	public Profile accessProfile() {
		return profile;
	}
	
	public MyMedias accessMyMedias() {
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
		return myMedias;
	}
	
	public Favorites accessFavorites() {
		updateFavorites(favorites);
		return favorites;
	}
	
	private void updateFavorites(Favorites favorites) {
		favorites = db.updateFavorites(favorites);
	}
	
	public AllMedias accessAllMedias() {
		return allMedias;
	}
	
	public void addMedia(Media media) {
		allMedias.addMedia(media);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
	}
	
	public void removeMedia(int key) {
		allMedias.removeMedia(key);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
	}
	
	public void editMedia(int key, Media media) {
		allMedias.setMedia(key, media);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
	}
	
	public void addInstitution(Institution institution) {
		db.addInstitution(institution);
	}
	
	public void deleteInstitution(int id) {
		db.deleteInstitution(id);
	}
	
}