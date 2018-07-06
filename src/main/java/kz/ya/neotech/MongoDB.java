/**
 * The Mongo class is designed to be thread safe and shared among threads.
 * Typically you create only 1 instance for a given DB cluster and use it across your app.
 * If for some reason you decide to create many mongo intances, note that:
 * all resource usage limits (max connections, etc) apply per mongo instance to dispose of an instance,
 * make sure you call mongo.close() to clean up resources
 */
package kz.ya.neotech;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author yerlana
 */
public enum MongoDB {
    
    CONNECTION;
    
    private MongoClient client = null;

    private MongoDB() {
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
            client = new MongoClient(uri);
        } catch (Exception e) {
            System.out.println("An error occured when connecting to MongoDB");
            throw e;
        }
    }

    public MongoClient getClient() {
        if (client == null)
            throw new RuntimeException();
        return client;
    }
    
    public MongoDatabase getDatabase() {
        return getClient().getDatabase("srm");
    }
    
    public MongoCollection<Document> getCollection() {
        return getDatabase().getCollection("task");
    }
}
