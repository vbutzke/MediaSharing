package database.dtos;

import java.io.IOException;
import java.util.LinkedList;
import app.entities.AbstractEntity;
import app.entities.Author;
import app.entities.Media;
import app.singletons.MediaStorageController;
import app.singletons.MediaType;

public class MediaDTO extends AbstractDTO {

	private String name;
	private String description;
	private MediaType type;
	private LinkedList<Author> authors;
	private String content;
	
	public MediaDTO(String name, String description, MediaType type, LinkedList<Author> authors, String content) {
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
	
	public String getContent() { 
		return content;
	}

	public void setContent(String content) { 
		this.content = content;
	}

	@Override
	public AbstractEntity toEntity() throws IOException {
		return new Media(name, description, type, authors, MediaStorageController.INSTANCE.getMedia(content));
	}
	
}
