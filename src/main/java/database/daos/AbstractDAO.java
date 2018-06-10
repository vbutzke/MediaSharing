package database.daos;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.util.JSON;

import database.DatabaseManagement;

public abstract class AbstractDAO {
	
	protected ObjectMapper mapper;
	protected JSON JSONInstance;
	protected MongoCollection<Document> dao;
	private DatabaseManagement dm;
	
	public AbstractDAO(DatabaseManagement dm) {
		mapper = new ObjectMapper();
		setDm(dm);
	}
	
	public Document createDocument(Object o) throws JsonProcessingException {
		String json = getJSON(o);
		Document d = new Document();
		d = Document.parse(json);
		return d;
	}
	
	public void addDocument(Document d) {
		dao.insertOne(d);
	}
	
	public void updateDocument(Bson filter, Bson update) {
		dao.findOneAndUpdate(filter, update);
	}
	
	public void remove(Bson filter) {
		dao.deleteOne(filter);
	}
	
	public FindIterable<Document> findAll(Bson conditions) {
		return dao.find(conditions);
	}
	
	public FindIterable<Document> findAll(){
		return dao.find();
	}
	
	public Document findOne(Bson conditions) {
		return dao.findOneAndDelete(conditions);
	}
	
	public DeleteResult deleteOne(Bson conditions) {
		return dao.deleteOne(conditions);
	}
	
	public String getJSON(Object value) throws JsonProcessingException {
		return mapper.writeValueAsString(value);
	}

	public DatabaseManagement getDm() {
		return dm;
	}

	public void setDm(DatabaseManagement dm) {
		this.dm = dm;
	}
	
}
