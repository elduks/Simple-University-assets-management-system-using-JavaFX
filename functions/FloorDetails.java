package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Gigara
 */
public class FloorDetails {
    public TableView<roomdetails> floordetailTable;
    public TableColumn<roomdetails,String> name;
    public TableColumn<roomdetails,String> department;
    public TableColumn<roomdetails,String> spacetype;
    public TableColumn<roomdetails,Double> GFA;
    public TableColumn<roomdetails,Double> UFA;
    public TableColumn<roomdetails, String> furniture;
    public Label floorLabel;
    public static Stage addroomStage;

    public void initialize() {
        if(FloorPlan.a == 1){
            floorLabel.setText("Floor 1 Details");
        }else if(FloorPlan.a == 2){
            floorLabel.setText("Floor 2 Details");
        }
        DBCollection gigaCollection = DatabaseConnect.database("floors");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        department.setCellValueFactory(new PropertyValueFactory<>("Dept"));
        spacetype.setCellValueFactory(new PropertyValueFactory<>("Spacetype"));
        GFA.setCellValueFactory(new PropertyValueFactory<>("GFA"));
        UFA.setCellValueFactory(new PropertyValueFactory<>("UFA"));
        furniture.setCellValueFactory(new PropertyValueFactory<>("Furniture"));
        //floordetailTable.getItems().add(new roomdetails("129G","Science","Vacant",15.0,20.0,"Mel Desk\nMel Bookcase\nMel Bookcase\nGas lift Chair"));
        //floordetailTable.getItems().add(new roomdetails("129F","Science","Vacant",12.0,17.0,"Pine Desk\nPine Bookcase\nPine Filing Cab.\nGas lift Chair"));

        Iterator<DBObject> fields = gigaCollection.find().iterator();



        while (fields.hasNext()) {
            int count = 0;
            BasicDBObject objects = (BasicDBObject) fields.next();
            Object floor = objects.get("Floor");

            Object name = objects.get("Name");
            Object Dept = objects.get("Department");
            Object Space = objects.get("Space");
            Object UFA = objects.get("UFA");
            Object GFA = objects.get("GFA");
            Object Furnitures = objects.get("Furniture");
            try {
                if (floor.toString().equals(String.valueOf(FloorPlan.a))) {
                    floordetailTable.getItems().add(new roomdetails(name.toString(), Dept.toString(), Space.toString(), Double.parseDouble(UFA.toString()), Double.parseDouble(GFA.toString()), Furnitures.toString()));
                }
            } catch (NullPointerException e) {

            }
        }

    }

    public void addNewRoom(MouseEvent mouseEvent) throws IOException {
        Scene addnewRoom = new Scene(FXMLLoader.load(getClass().getResource("AddNewRoom.fxml")));
        addroomStage = new Stage();
        addroomStage.setTitle("Add New Room");
        addroomStage.setScene(addnewRoom);
        addroomStage.show();
        FloorPlan.newstage.close();

    }

    public class roomdetails extends FloorDetails{
        String name,Dept,Spacetype, Furniture;
        Double UFA,GFA;
        public roomdetails(String name, String Dept, String SpaceType, Double UFA, Double GFA, String Furniture){
            this.name = name;
            this.Dept = Dept;
            this.Spacetype = SpaceType;
            this.UFA = UFA;
            this.GFA = GFA;
            this.Furniture = Furniture;

        }

        public String getName() {
            return name;
        }

        public String getDept() {
            return Dept;
        }

        public String getSpacetype() {
            return Spacetype;
        }

        public Double getUFA() {
            return UFA;
        }

        public Double getGFA() {
            return GFA;
        }

        public String getFurniture() {
            return Furniture;
        }
    }
}
