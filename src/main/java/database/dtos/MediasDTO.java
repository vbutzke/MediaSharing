package database.dtos;

import java.util.HashMap;

import app.entities.AbstractEntity;
import app.entities.Media;
import app.entities.Medias;
import app.singletons.MediaCollectionType;

public class MediasDTO extends AbstractDTO {
	
	private HashMap<Integer, Media> medias;
	private MediaCollectionType type;

	public MediasDTO() {
		
	}
	
	public MediaCollectionType getCollectionType() {
		return type;
	}
	
	public void setCollectionType(MediaCollectionType type) {
		this.type = type;
	}
	
	public HashMap<Integer, Media> getMedias() {
		return medias;
	}

	public void setMedias(HashMap<Integer, Media> medias) {
		this.medias = medias;
	}
	
	@Override
	public AbstractEntity toEntity() {
		Medias m = new Medias();
		m.setCollectionType(type);
		m.setMedias(medias);
		m.setLastKey(medias.size());
		return m;
	}

}
