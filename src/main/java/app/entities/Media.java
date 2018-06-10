package app.entities;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import app.singletons.MediaStorageController;
import app.singletons.MediaType;
import database.dtos.AbstractDTO;
import database.dtos.MediaDTO;

public class Media extends AbstractEntity {
	
	private String name;
	private String description;
	private MediaType type;
	private LinkedList<Author> authors;
	private File content;

	public Media(String name, String description, MediaType type, LinkedList<Author> authors, File content) {
		this.name 		 = name;
		this.description = description;
		this.type		 = type;
		this.authors	 = authors;
		this.content 	 = content;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MediaType getType() {
		return type;
	}
	
	public void setType(MediaType type) {
		this.type = type;
	}
	
	public LinkedList<Author> getAuthors() {
		return authors;
	}
	
	public void setAuthors(LinkedList<Author> authors) {
		this.authors = authors;
	}
	
	public File getContent() {
		return content;
	}

	public void setContent(File content) {
		this.content = content;
	}

	@Override
	public AbstractDTO convertToDTO() throws IOException {
	
		File storedMedia = MediaStorageController.INSTANCE.getMedia(name);
		
		if(storedMedia == null) {
			MediaStorageController.INSTANCE.saveMedia(content, name);
		} else {
			if(content.equals(storedMedia)) {
				return new MediaDTO(name, description, type, authors, name);
			} else {
				MediaStorageController.INSTANCE.removeMedia(name);
				MediaStorageController.INSTANCE.saveMedia(content, name);
			}
		}
		
		return new MediaDTO(name, description, type, authors, name);
	}
	
}