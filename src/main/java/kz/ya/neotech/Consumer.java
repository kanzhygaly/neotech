package kz.ya.neotech;

import com.mongodb.client.MongoCollection;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import org.bson.Document;

/**
 *
 * @author yerlana
 */
public class Consumer implements Runnable {

    private final BlockingQueue<Date> queue;

    public Consumer(BlockingQueue<Date> queue) {
        this.queue = queue;
    }
 
    @Override
    public void run() {
        while (true) {
            try {
                MongoCollection<Document> collection = MongoDB.CONNECTION.getCollection();
                Date timestamp = queue.take();
                Document document = new Document("timestamp", timestamp);
                collection.insertOne(document);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                try {
                    Thread.sleep(5000);
                    System.out.println("Trying to reconnect...");
                } catch (InterruptedException ex1) {                
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
