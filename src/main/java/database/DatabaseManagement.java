package database;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DatabaseManagement {

    private MongoClient mongoClient;
    private MongoDatabase database;
    //users //mymedias //favorites
    //allMedia

    public void startDB(){
        createDB();
        createCollections();
    }

    private void createDB(){
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        this.mongoClient = new MongoClient(connectionString);
        this.database = mongoClient.getDatabase("MediaSharing");
    }

    private void createCollections(){
        // this.students = database.getCollection("students");
        // this.passwords = database.getCollection("passwords");
    }

    public void closeConnection(){
        this.mongoClient.close();
    }

}
