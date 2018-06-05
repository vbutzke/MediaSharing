package database.dtos;

import java.util.HashMap;

import app.entities.Institution;

public class UserDTO {

	private String name;
	private HashMap<Integer, String> email;
	private String password;
	private HashMap<Integer, Institution> institution;
	private MediasDTO medias; //ponteiro
	private MediasDTO favorites; //

    public UserDTO(String name, HashMap<Integer, String> email, String password, HashMap<Integer, Institution> institution){
    	this.name 		 = name;
    	this.email 		 = email;
    	this.password    = password;
    	this.institution = institution;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<Integer, String> getEmail() {
		return email;
	}

	public void setEmail(HashMap<Integer, String> email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HashMap<Integer, Institution> getInstitution() {
		return institution;
	}

	public void setInstitution(HashMap<Integer, Institution> institution) {
		this.institution = institution;
	}

	public MediasDTO getMedias() {
		return medias;
	}

	public void setMedias(MediasDTO medias) {
		this.medias = medias;
	}

	public MediasDTO getFavorites() {
		return favorites;
	}

	public void setFavorites(MediasDTO favorites) {
		this.favorites = favorites;
	}
	
}