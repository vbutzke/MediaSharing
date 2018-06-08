package app.entities;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import app.singletons.MediaType;
import database.dtos.AbstractDTO;
import database.dtos.MediaDTO;

public class Media extends AbstractEntity {
	
	private String name;
	private String description;
	private BufferedImage photo;
	private MediaType type;
	private LinkedList<Author> authors;
	//private ..  content;
	
	public Media(String name, String description, BufferedImage photo, MediaType type, LinkedList<Author> authors/*, ... content*/) {
		this.name 		 = name;
		this.description = description;
		this.photo 		 = photo;
		this.type		 = type;
		this.authors	 = authors;
		//this.content = content;
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
	
	public BufferedImage getPhoto() {
		return photo;
	}
	
	public void setPhoto(BufferedImage photo) {
		this.photo = photo;
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
	
	@Override
	public AbstractDTO convertToDTO() {
		return new MediaDTO(name, description, photo, type, authors);
	}
	
}