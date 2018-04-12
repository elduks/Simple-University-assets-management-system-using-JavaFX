package functions;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Created by Gigara
 */
public class DatabaseConnect {
    public static DBCollection database(String type) {
        MongoClient gigaclient = new MongoClient("localhost", 27017);
        //System.out.println("Server ok");
        //System.out.println(gigaclient.getDatabaseNames());
        DB gigabase = gigaclient.getDB("cw3");

        DBCollection gigacollection = gigabase.getCollection(type);
        //System.out.println("Collection ok");
        return gigacollection;
    }
}
