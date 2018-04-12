package login.user;

import com.mongodb.*;



/**
 * Created by Gigara
 */
public class users {
    public static String loggeduser ;
    public static String loggeduserType;

    public static DBCollection database(String type) {
        MongoClient gigaclient = new MongoClient("localhost", 27017);
        //System.out.println("Server ok");
        //System.out.println(gigaclient.getDatabaseNames());
        DB gigabase = gigaclient.getDB("cw3");

        DBCollection gigacollection = gigabase.getCollection(type);
        //System.out.println("Collection ok");
        return gigacollection;
    }

    public static void usercreate(String username, String password,String userType) {
        DBCollection gigacollection = database("users");

        BasicDBObject userdetails = new BasicDBObject();
        userdetails.put("username", username);
        userdetails.put("password", password);
        userdetails.put("Type",userType);

        gigacollection.insert(userdetails);
    }

    public static boolean uservalid(String username) {
        DBCollection gigacollection = database("users");

        if (gigacollection.find(new BasicDBObject("username", username)).count() > 0) {
            //System.out.println("found");
            return true;
        } else
            return false;
    }

    public static String newpsw(String username, String password, String newpassword) {
        DBCollection gigacollection = database("users");

        if (uservalid(username,password)) {
            gigacollection.update(new BasicDBObject("username", username), new BasicDBObject("$set", new BasicDBObject("password", newpassword)));
            String success = "Password Successfully Changed";
            return success;
        } else {
            String err = "Please enter valid credentials";
            return err;
        }
    }
    public static boolean uservalid(String username, String password){
        DBCollection gigacollection = database("users");

        if ((gigacollection.find(new BasicDBObject("username", username)).count() > 0) &&
                (((gigacollection.find(new BasicDBObject("username", username)).one().get("password"))).equals(password))){
            loggeduser = username;

            if(gigacollection.find(new BasicDBObject("username",username)).one().get("Type").equals("Admin")){
                loggeduserType = "Admin";
            }else
            {
                loggeduserType = "Normal";
            }
            return true;
        }else return false;
    }
}
