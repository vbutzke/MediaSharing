package database.dtos;

import java.io.IOException;
import java.util.HashMap;

import app.entities.AbstractEntity;
import app.entities.Media;
import app.entities.Medias;
import app.singletons.MediaCollectionType;

public class MediasDTO extends AbstractDTO {
	
	private HashMap<Integer, MediaDTO> medias;
	private MediaCollectionType type;

	public MediasDTO() {
		
	}
	
	public MediaCollectionType getCollectionType() {
		return type;
	}
	
	public void setCollectionType(MediaCollectionType type) {
		this.type = type;
	}
	
	public HashMap<Integer, MediaDTO> getMedias() {
		return medias;
	}

	public void setMedias(HashMap<Integer, MediaDTO> medias) {
		this.medias = medias;
	}
	
	@Override
	public AbstractEntity toEntity() throws IOException {
		Medias m = new Medias();
		m.setCollectionType(type);
		HashMap<Integer, Media> mediasHashMap = new HashMap<Integer, Media>();
		for(int i = 1; i<medias.size(); i++) {
			mediasHashMap.put(i, (Media) medias.get(i).toEntity());
		}
		m.setMedias(mediasHashMap);
		m.setLastKey(medias.size());
		return m;
	}

}
