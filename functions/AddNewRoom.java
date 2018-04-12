package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Iterator;

/**
 * Created by Gigara
 */
public class AddNewRoom {
    public ListView total;
    public ListView selected;
    public ComboBox deparmentList;
    public TextField room;
    public TextField GFA;
    public TextField UFA;
    public ComboBox spaceList;

    public void initialize(){

        DBCollection gigaCollection = DatabaseConnect.database("Furnitures");
        Iterator<DBObject> fields = gigaCollection.find().iterator();
        while (fields.hasNext()) {
            BasicDBObject objects = (BasicDBObject) fields.next();
            Object Name = objects.get("Name");
            Object Id = objects.get("ID");

            String detail = Name.toString() + " ("+Id.toString()+")";

            total.getItems().add(detail);
            }

        deparmentList.getItems().add("Science");
        deparmentList.getItems().add("Engineering");
        deparmentList.getItems().add("Computing");
        deparmentList.getItems().add("Studios");

        spaceList.getItems().add("Non Usable");
        spaceList.getItems().add("Office");
        spaceList.getItems().add("General Teaching");
        spaceList.getItems().add("Laboratory");
        spaceList.getItems().add("Ancillary");
        spaceList.getItems().add("Information Services");
        spaceList.getItems().add("Accommodation");

        deparmentList.getSelectionModel().select(0);
        spaceList.getSelectionModel().select(0);
    }

    public void add(MouseEvent mouseEvent) {
        try {
            if(total.getSelectionModel().getSelectedItem() != null) {
                selected.getItems().add(total.getSelectionModel().getSelectedItem());
                total.getItems().remove(total.getSelectionModel().getSelectedIndex());
            }
        }catch (Exception e){

        }

    }

    public void remove(MouseEvent mouseEvent) {
        try{
            if(selected.getSelectionModel().getSelectedItem() != null) {
                total.getItems().add(selected.getSelectionModel().getSelectedItem());
                selected.getItems().remove(selected.getSelectionModel().getSelectedIndex());
            }
        }catch (Exception e){

        }
    }

    public void addnewRoom(MouseEvent mouseEvent) {
        String furniturelist ="" ;
        for(int x=0;x<selected.getItems().size();x++){
            furniturelist += selected.getItems().get(0)+"\n";
            //System.out.println(furniturelist);
        }
        DBCollection gigaCollection = DatabaseConnect.database("floors");
        BasicDBObject details = new BasicDBObject();
        details.put("Floor",FloorPlan.a);
        details.put("Name",room.getText());
        details.put("Department", String.valueOf(deparmentList.getValue()));
        details.put("Space", String.valueOf(spaceList.getValue()));
        details.put("GFA", GFA.getText());
        details.put("UFA",UFA.getText());
        details.put("Furniture", furniturelist);

        gigaCollection.insert(details);

    }

}
