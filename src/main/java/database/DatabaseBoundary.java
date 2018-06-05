package database;

import java.util.HashMap;

import app.entities.AllMedias;
import app.entities.Favorites;
import app.entities.Institution;
import app.entities.Media;
import app.entities.MyMedias;
import app.entities.User;

public class DatabaseBoundary {
	//na hora que eu vou passar as coisas pro banco eu crio o dto e mudo as coisas que precisa
	
	public void removeAccount() {
		
	}
	
	public User getUser(String email) {
		User u;
		return u;
	}

	public Favorites getFavorites(String email) {
		Favorites f; //MediasDTO
		return f;
	}
	
	public MyMedias getMyMedias(String email) {
		MyMedias m; //MediasDTO
		return m;
	}
	
	public AllMedias getAllMedias() {
		AllMedias a; //MediasDTO
		return a;
	}
	
	public Favorites updateFavorites(Favorites favorites) {
		HashMap <Integer, Media> mediasF = favorites.getMedias();
		Favorites temp = new Favorites();
		
		for(int i=1;i<mediasF.size(); i++) {
			Media m = mediasF.get(i);
			temp.addMedia(allMedias.searchMedia(m.getName(), m.getDescription(),m.getAuthors(), m.getType()));
		}
		
		return temp;

	}
	
	public void addInstitution(Institution institution) {
		
	}
	
	public void deleteInstitution(int id) {
		
	}
	
}