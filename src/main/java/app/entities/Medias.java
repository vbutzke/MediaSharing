package app.entities;

import java.util.HashMap;
import java.util.LinkedList;

import app.singletons.MediaCollectionType;
import app.singletons.MediaType;
import app.singletons.SearchEngine;
import database.dtos.AbstractDTO;
import database.dtos.MediasDTO;

public class Medias extends AbstractEntity {
	
	private HashMap<Integer, Media> medias;
	private MediaCollectionType type;
	private int lastKey;
	
	public Medias() {
		
	}
	
	public MediaCollectionType getCollectionType() {
		return type;
	}
	
	public void setCollectionType(MediaCollectionType type) {
		this.type = type;
	}
	
	public void addMedia(Media media) {
		medias.put(lastKey, media);
		lastKey = medias.size()+1;
	}
	
	public Media removeMedia(int key) {
		lastKey = medias.size();
		return medias.remove(key);
	}
	
	public HashMap<Integer, Media> searchMedia(String name, String description, LinkedList<Author> authors, MediaType mediaType){
		return SearchEngine.INSTANCE.search(name, description, authors, mediaType, medias);
	}

	public HashMap<Integer, Media> getMedias() {
		return medias;
	}

	public void setMedias(HashMap<Integer, Media> medias) {
		this.medias = medias;
		lastKey = medias.size()+1;
	}

	public int getLastKey() {
		return lastKey;
	}

	public void setLastKey(int lastKey) {
		this.lastKey = lastKey;
	}
	
	public Media getMedia(int key) {
		return medias.get(key);
	}
	
	public void setMedia(int key, Media media) {
		medias.put(key, media);
	}
	
	@Override
	public AbstractDTO convertToDTO() {
		MediasDTO dto = new MediasDTO();
		dto.setMedias(getMedias());
		return dto;
	}
	
}