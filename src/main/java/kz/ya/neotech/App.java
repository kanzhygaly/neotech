package kz.ya.neotech;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author yerlana
 */
public class App {

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger("com.mongodb");
        mongoLogger.setLevel(Level.SEVERE);

        if (args.length == 0) {
            BlockingQueue<Date> queue = new LinkedBlockingDeque<>();
            int N = Runtime.getRuntime().availableProcessors();
            System.out.println("Number of processors: " + N);
            ExecutorService executorService = Executors.newFixedThreadPool(N);

            Consumer consumer = new Consumer(queue);
            executorService.submit(consumer);

            while (true) {
                try {
                    queue.put(new Date());
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            if (args[0].equals("-p")) {
                try {
                    MongoCollection<Document> collection = MongoDB.CONNECTION.getCollection();
                    MongoCursor<Document> it = collection.find().iterator();
                    while (it.hasNext()) {
                        System.out.println(it.next().get("timestamp"));
                    }
                } catch (Exception ex) {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Trying to reconnect...");
                    } catch (InterruptedException ex1) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else if (args[0].equals("-c")) {
                try {
                    MongoDB.CONNECTION.getCollection().drop();
                    System.out.println("DB is cleaned up");
                } catch (Exception ex) {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Trying to reconnect...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
