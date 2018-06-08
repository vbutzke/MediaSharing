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
//have to actually persist everything later, remember, you have to call DatabaseController for that
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
		this.author.add((Author) profile.getUser()); //?
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
	
	public Medias accessMyMedias() {
		myMedias.setMedias(allMedias.searchMedia("", "", author, null));
		return myMedias;
	}
	
	public Medias accessFavorites() throws JsonProcessingException {
		updateFavorites(favorites);
		return favorites;
	}
	
	private void updateFavorites(Medias favorites) throws JsonProcessingException {
		favorites = (Medias) (db.updateFavorites(profile.getUser(), favorites)).toEntity();
	}
	
	public Medias accessAllMedias() {
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