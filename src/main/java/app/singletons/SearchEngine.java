package app.singletons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import app.entities.Author;
import app.entities.Media;
import app.entities.Medias;

public enum SearchEngine {
	
	INSTANCE;
	
	private Medias medias;
	
	private SearchEngine() {
	}
	
	private void setMedias(HashMap<Integer, Media> medias) {
		this.medias.setMedias(medias);
		this.medias.setCollectionType(MediaCollectionType.ALL_MEDIAS);
	}
	
	public HashMap<Integer, Media> search(String name, String description, LinkedList<Author> authors, MediaType mediaType, HashMap<Integer, Media> mediasSet) {
		
		boolean validName 		 	   = isCriteriaNull(name);
		boolean validDescription	   = isCriteriaNull(description);
		boolean validAuthors 	 	   = isCriteriaNull(authors);
		boolean validType 			   = isCriteriaNull(mediaType);
		int lastKey 			 	   = 0;
		HashMap<Integer, Media> result = new HashMap<Integer, Media>();
		
		setMedias(mediasSet);
		
		for(Entry<Integer, Media> entry : medias.getMedias().entrySet()) {
			 Media v = entry.getValue();
			 
			 if(validName) {
				result = matchName(name, v, result, lastKey);
				lastKey = result.size()+1;
			 } 
	
			 if(validDescription) {
				 result = matchDescription(description, v, result, lastKey);
				 lastKey = result.size()+1;
			 } 
				
			 if(validAuthors) {
				 result = matchAuthors(authors, v, result, lastKey);
				 lastKey = result.size()+1;
			 } 
				
			 if(validType) {
				 result = matchMediaType(mediaType, v, result, lastKey);
				 lastKey = result.size()+1;
			 } 
			 
		}

		return result;
		
	}
	
	private HashMap<Integer, Media> matchName(String name, Media value, HashMap<Integer, Media> result, int lastKey){
		if(name.equals(value.getName())) {
			result.put(lastKey, value);
		}
		
		return result;
	}
	
	private HashMap<Integer, Media> matchDescription(String description, Media value, HashMap<Integer, Media> result, int lastKey){
		if(description.equals(value.getDescription())) {
			result.put(lastKey, value);
		}
		
		return result;
	}
	
	private HashMap<Integer, Media> matchAuthors(LinkedList<Author> authors, Media value, HashMap<Integer, Media> result, int lastKey){
		if(value.getAuthors().containsAll(authors)) {
			result.put(lastKey, value);
		}
		
		return result;
	}
	
	private HashMap<Integer, Media> matchMediaType(MediaType type, Media value, HashMap<Integer, Media> result, int lastKey){
		if(type == value.getType()) {
			result.put(lastKey, value);
		}
		
		return result;
	}

	private boolean isCriteriaNull(Object criteria) {
		if(criteria!=null) {
			return false;
		}
		return true;
	}
	
}
