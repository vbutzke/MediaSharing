package database;

import java.io.IOException;
import java.util.LinkedList;
import org.bson.Document;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import app.entities.*;
import database.daos.*;
import database.dtos.*;

public class DatabaseController {

	private AbstractDAO dao;
	private AbstractDTO dto;
	ObjectMapper mapper;
	int institutionIDs;
	DatabaseManagement dm;
	
	public DatabaseController() {
		mapper = new ObjectMapper();
		institutionIDs = getLastInstitutionID();
	}
	
	public void addAccount(User user) throws JsonProcessingException {
		dao = new UserDAO();
		dto = user.convertToDTO();
		Document userRecord = dao.createDocument(dto);
		dm.addUser(userRecord);
	}
	
	public void removeAccount(User user) {
		dao = new UserDAO();
		dto = user.convertToDTO(); 
		dm.removeUser(((UserDTO) dto).getEmail());
	}
	
	public UserDTO getUser(String email) throws JsonParseException, JsonMappingException, IOException {
		dao = new UserDAO();
		Document document = dao.findOne(Filters.eq("email", email));
		return mapper.readValue(document.toJson(), UserDTO.class);
	}
	
	public void addEmail(User user, String email) {
		
		
	}
	
	public void removeEmail(User user, int id) {
		
		
	}
	
	public void addInstitutionToUser(User user, Institution institution) {
		
		
	}
	
	public void removeInstitutionFromUser(User user, int id) {
		
		
	}
	
	public void changePassword(User user, String password) {
		
		
	}
	
	public void changeName(User user, String name) {
		
		
	}

	public MediasDTO getFavorites(String email) throws JsonParseException, JsonMappingException, IOException {
		return ((UserDTO)getUser(email)).getFavorites();
	}
	
	public MediasDTO getMyMedias(String email) throws JsonParseException, JsonMappingException, IOException {
		return ((UserDTO)getUser(email)).getMedias();
	}
	
	public MediasDTO getAllMedias() throws JsonParseException, JsonMappingException, IOException {
		dao = new MediasDAO();
		FindIterable<Document> documents = dao.findAll();
		
		for(Document doc : documents) {
			return mapper.readValue(doc.toJson(), MediasDTO.class);
		}
		
		return null;
	}
	
	public MediasDTO updateFavorites(User user, Medias favorites) throws JsonProcessingException {
		dao = new UserDAO();
		dto = (UserDTO) user.convertToDTO();
		Document document = dao.findOne(Filters.eq("email", (((UserDTO) dto).getEmail())));
		((UserDTO) dto).setFavorites((MediasDTO) favorites.convertToDTO());
		dao.updateDocument(document, new Document("$set", dao.createDocument(dto)));
		return (MediasDTO) dto;
	}
	
	public InstitutionDTO getInstitution(String emailProvider) throws JsonParseException, JsonMappingException, IOException {
		dao = new InstitutionDAO();
		Document d = ((InstitutionDAO)dao).findOne(Filters.eq("emailProvider", emailProvider));
		return mapper.readValue(d.toJson(), InstitutionDTO.class);
	}
	
	public void addInstitution(Institution institution) throws JsonProcessingException {
		dao = new InstitutionDAO();
		InstitutionDTO iDTO = new InstitutionDTO(institution.getName(), institution.getCNPJ(), institution.getEmailProvider());
		dao.addDocument(dao.createDocument(iDTO).append("id", ++institutionIDs));
	}
	
	public void deleteInstitution(int id) {
		dao = new InstitutionDAO();
		dao.deleteOne(Filters.eq("id", id));
	}
	
	public boolean isCodeValid(String code) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		dao = new AccessCodesDAO();
		Document validCodes = dao.findOne(Filters.eq("used", false));
		dto = mapper.readValue(dao.getJSON(validCodes), AccessCodesDTO.class);
		
		if(((AccessCodesDTO) dto).getAccessCodeIndex(code) != -1) {
			((AccessCodesDTO) dto).removeAccessCode(code);
			Document invalidCodes = dao.findOne(Filters.eq("used", true));
			dto = mapper.readValue(dao.getJSON(invalidCodes), AccessCodesDTO.class);
			((AccessCodesDTO) dto).addAccessCode(code);
			return true;
		}
		
		return false;
	}
	
	public void addAccessCode(String code) throws IOException {
		dao = new AccessCodesDAO();
		dto = new AccessCodesDTO();
		FindIterable<Document> record = dao.findAll();
		
		if(record == null) {
			LinkedList<String> list = new LinkedList<String>();
			list.add(code);
			dao.createDocument(list);
		} else {
			for(Document r : record) {
				dto = mapper.readValue(dao.getJSON(r), AccessCodesDTO.class);
				((AccessCodesDTO) dto).addAccessCode(code);
				dao.updateDocument(r, new Document("$set", new Document("accessCodes", ((AccessCodesDTO) dto).getAllAccessCodes())));
			}
		}
	}
	
	private int getLastInstitutionID() {
		dao = new InstitutionDAO();
		FindIterable<Document> institutions = dao.findAll();
		int highest = 0;
		int lowest = 0;
		
		for(Document document : institutions) {
			lowest = (int) document.get("id");
			if(lowest > highest) {
				int temp = highest;
				highest = lowest;
				lowest = temp;
			}
		}
		return highest;
	}
	
}