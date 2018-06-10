package database;

import java.util.HashMap;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DatabaseManagement {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> users;
    private MongoCollection<Document> accessCodes;
    private MongoCollection<Document> allMedias;
    private MongoCollection<Document> institutions;

    public void startDB(){
        createDB();
        createCollections();
    }

    private void createDB(){
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        this.mongoClient 				= new MongoClient(connectionString);
        this.database 					= mongoClient.getDatabase("MediaSharing");
    }

    private void createCollections(){
    	this.users		  = database.getCollection("users");
    	this.accessCodes  = database.getCollection("accessCodes");
    	this.allMedias 	  = database.getCollection("allMedias");
    	this.institutions = database.getCollection("intitutions");
    }
    
    public void addUser(Document user) {
    	users.insertOne(user);
    }
    
    public void removeUser(HashMap<Integer, String> email) {
    	users.deleteOne(Filters.elemMatch("email", Filters.eq("1", email.get(1))));
    }

    public void closeConnection(){
        this.mongoClient.close();
    }

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}

	public MongoCollection<Document> getUsers() {
		return users;
	}

	public void setUsers(MongoCollection<Document> users) {
		this.users = users;
	}

	public MongoCollection<Document> getAccessCodes() {
		return accessCodes;
	}

	public void setAccessCodes(MongoCollection<Document> accessCodes) {
		this.accessCodes = accessCodes;
	}

	public MongoCollection<Document> getAllMedias() {
		return allMedias;
	}

	public void setAllMedias(MongoCollection<Document> allMedias) {
		this.allMedias = allMedias;
	}

	public MongoCollection<Document> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(MongoCollection<Document> institutions) {
		this.institutions = institutions;
	}

}
