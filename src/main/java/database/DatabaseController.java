package database;

import java.io.IOException;
import java.util.LinkedList;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import app.entities.*;
import app.singletons.MediaCollectionType;
import app.singletons.MediaStorageController;
import app.singletons.UserType;
import database.daos.*;
import database.dtos.*;

public class DatabaseController {

	private AbstractDAO dao;
	private AbstractDTO dto;
	private ObjectMapper mapper;
	private int institutionIDs;
	private DatabaseManagement dm;
	
	public DatabaseController(DatabaseManagement dm) {
		mapper 		   = new ObjectMapper();
		institutionIDs = getLastInstitutionID();
		this.dm 	   = dm;
		MediaStorageController.INSTANCE.setGFSMedia(this.dm);
	}
	
	public void addAccount(User user) throws JsonProcessingException {
		dao = new UserDAO(dm);
		dto = user.convertToDTO();
		Document userRecord = dao.createDocument(dto);
		dm.addUser(userRecord);
	}
	
	public void removeAccount(User user) {
		dao = new UserDAO(dm);
		dto = user.convertToDTO(); 
		dm.removeUser(((UserDTO) dto).getEmail());
	}
	
	public UserDTO getUser(String email) throws JsonParseException, JsonMappingException, IOException {
		dao = new UserDAO(dm);
		Document document = dao.findOne(Filters.eq("email", email));
		return mapper.readValue(document.toJson(), UserDTO.class);
	}
	
	public void addEmail(User user, String email) throws JsonProcessingException {
		user.addEmail(email);
		updateUser(user);
	}
	
	public void removeEmail(User user, int id) throws JsonProcessingException {
		user.removeEmail(id);
		updateUser(user);
	}
	
	public void addInstitutionToUser(User user, Institution institution) throws JsonProcessingException {
		user.addInstitution(institution);
		updateUser(user);
	}
	
	public void removeInstitutionFromUser(User user, int id) throws JsonProcessingException {
		user.removeInstitution(id);
		updateUser(user);
	}
	
	public void changePassword(User user, String password) throws JsonProcessingException {
		user.setPassword(password);
		updateUser(user);
	}
	
	public void changeName(User user, String name) throws JsonProcessingException {
		user.setName(name);
		updateUser(user);
	}
	
	public UserDTO changeToAuthor(User user) throws JsonProcessingException {
		user.setUserType(UserType.AUTHOR);
		updateUser(user);
		return (UserDTO) user.convertToDTO();
	}

	public MediasDTO getFavorites(String email) throws JsonParseException, JsonMappingException, IOException {
		return ((UserDTO)getUser(email)).getFavorites();
	}
	
	public MediasDTO getMyMedias(String email) throws JsonParseException, JsonMappingException, IOException {
		return ((UserDTO)getUser(email)).getMedias();
	}
	
	public MediasDTO getAllMedias() throws JsonParseException, JsonMappingException, IOException {
		dao = new MediasDAO(dm);
		FindIterable<Document> documents = dao.findAll();
		
		for(Document doc : documents) {
			return mapper.readValue(doc.toJson(), MediasDTO.class);
		}
		
		return null;
	}
	
	public MediasDTO updateFavorites(User user, Medias favorites) throws IOException {
		return updateUserMedias(user, favorites, Filters.eq("email", (((UserDTO) dto).getEmail())));
	}
	
	public MediasDTO updateMyMedias(User user, Medias myMedias) throws IOException {
		return updateUserMedias(user, myMedias, Filters.eq("email", (((UserDTO) dto).getEmail())));
	}
	
	public MediasDTO updateAllMedias(Medias allMedias) throws IOException {
		dao = new MediasDAO(dm);
		dto = (MediasDTO) allMedias.convertToDTO();
		Document document = dao.findOne(Filters.eq("type", MediaCollectionType.ALL_MEDIAS));
		dao.updateDocument(document, new Document("$set", dao.createDocument(dto)));
		return (MediasDTO) dto;
	}
	
	public InstitutionDTO getInstitution(String emailProvider) throws JsonParseException, JsonMappingException, IOException {
		dao = new InstitutionDAO(dm);
		Document d = ((InstitutionDAO)dao).findOne(Filters.eq("emailProvider", emailProvider));
		return mapper.readValue(d.toJson(), InstitutionDTO.class);
	}
	
	public void addInstitution(Institution institution) throws JsonProcessingException {
		dao = new InstitutionDAO(dm);
		InstitutionDTO iDTO = new InstitutionDTO(institution.getName(), institution.getCNPJ(), institution.getEmailProvider());
		dao.addDocument(dao.createDocument(iDTO).append("id", ++institutionIDs));
	}
	
	public void deleteInstitution(int id) {
		dao = new InstitutionDAO(dm);
		dao.deleteOne(Filters.eq("id", id));
	}
	
	public boolean isCodeValid(String code) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		dao = new AccessCodesDAO(dm);
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
		dao = new AccessCodesDAO(dm);
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
	
	public AbstractDAO getDao() {
		return dao;
	}

	public void setDao(AbstractDAO dao) {
		this.dao = dao;
	}

	public AbstractDTO getDto() {
		return dto;
	}

	public void setDto(AbstractDTO dto) {
		this.dto = dto;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public int getInstitutionIDs() {
		return institutionIDs;
	}

	public void setInstitutionIDs(int institutionIDs) {
		this.institutionIDs = institutionIDs;
	}

	public DatabaseManagement getDm() {
		return dm;
	}

	public void setDm(DatabaseManagement dm) {
		this.dm = dm;
	}

	private int getLastInstitutionID() {
		dao = new InstitutionDAO(dm);
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
	
	private void updateUser(User user) throws JsonProcessingException {
		dao = new UserDAO(dm);
		dto = user.convertToDTO();
		Document document = dao.findOne(Filters.eq("email", (((UserDTO) dto).getEmail())));
		dao.updateDocument(document, new Document("$set", dao.createDocument(dto)));
	}
	
	private MediasDTO updateUserMedias(User user, Medias medias, Bson filters) throws IOException {
		dao = new UserDAO(dm);
		dto = (UserDTO) user.convertToDTO();
		Document document = dao.findOne(filters);
		((UserDTO) dto).setFavorites((MediasDTO) medias.convertToDTO());
		dao.updateDocument(document, new Document("$set", dao.createDocument(dto)));
		return (MediasDTO) dto;
	}
	
}