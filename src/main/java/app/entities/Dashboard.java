package app.entities;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import app.singletons.MediaCollectionType;
import app.singletons.MediaType;
import database.DatabaseController;

public class Dashboard {
	
	private Profile profile;
	private Medias myMedias;
	private Medias favorites;
	private Medias allMedias;
	private DatabaseController db;
	private LinkedList<Author> author; //?
	
	public Dashboard(String email) throws JsonParseException, JsonMappingException, IOException {
		this.db 	   = new DatabaseController();
		this.profile   = new Profile((User) db.getUser(email).toEntity(), db);
		this.myMedias  = (Medias) db.getMyMedias(email).toEntity();
		this.favorites = (Medias) db.getFavorites(email).toEntity();
		this.allMedias = (Medias) db.getAllMedias().toEntity();
		this.myMedias.setCollectionType(MediaCollectionType.MY_MIDIAS);
		this.favorites.setCollectionType(MediaCollectionType.FAVORITES);
		this.allMedias.setCollectionType(MediaCollectionType.ALL_MEDIAS);
	}
	
	public HashMap<Integer, Media> searchMedia(String name, String description, LinkedList<Author> authors, MediaType mediaType) {
		return allMedias.searchMedia(name, description, authors, mediaType);
	}
	
	public Profile accessProfile() {
		return profile;
	}
	
	public Medias accessMyMedias() throws IOException {
		updateMyMedias();
		return myMedias;
	}
	
	public Medias accessFavorites() throws IOException {
		updateFavorites();
		return favorites;
	}
	
	public Medias accessAllMedias() throws IOException {
		updateMedias();
		return allMedias;
	}
	
	private void updateFavorites() throws IOException {
		favorites = (Medias) db.getFavorites(profile.getUser().getEmail().get(1)).toEntity();
	}
	
	private void updateMyMedias() throws IOException {
		favorites = (Medias) db.getMyMedias(profile.getUser().getEmail().get(1)).toEntity();
	}
	
	private void updateMedias() throws IOException {
		favorites = (Medias) db.getAllMedias().toEntity();
	}
		
	public void addMedia(Media media) throws JsonProcessingException {
		if(author.isEmpty()) {
			Author a = new Author(profile.getUser(), media);
			profile.changeToAuthor();
			author.add(a);
		}
		allMedias.addMedia(media);
		db.updateAllMedias(allMedias);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
		db.updateMyMedias(profile.getUser(), myMedias);
	}
	
	public void removeMedia(int key) throws JsonProcessingException {
		allMedias.removeMedia(key);
		db.updateAllMedias(allMedias);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
		db.updateMyMedias(profile.getUser(), myMedias);
	}
	
	public void editMedia(int key, Media media) throws JsonProcessingException {
		allMedias.setMedia(key, media);
		db.updateAllMedias(allMedias);
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
		db.updateMyMedias(profile.getUser(), myMedias);
	}
	
	public void addInstitution(Institution institution) throws JsonProcessingException {
		db.addInstitution(institution);
	}
	
	public void deleteInstitution(int id) {
		db.deleteInstitution(id);
	}
	
	public void addAccessCode(String code) throws IOException {
		db.addAccessCode(code);
	}
	
}