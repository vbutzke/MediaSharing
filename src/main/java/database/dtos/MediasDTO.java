package database.dtos;

import java.util.HashMap;
import app.entities.Media;

public class MediasDTO {
	
	private HashMap<Integer, Media> medias;

	public MediasDTO() {
		
	}
	
	public HashMap<Integer, Media> getMedias() {
		return medias;
	}

	public void setMedias(HashMap<Integer, Media> medias) {
		this.medias = medias;
	}

}
