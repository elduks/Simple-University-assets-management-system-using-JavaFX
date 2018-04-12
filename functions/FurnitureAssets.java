package functions;

import com.jfoenix.controls.JFXDatePicker;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Gigara
 */
public class FurnitureAssets {
    public TableColumn<furniture,String> key;
    public TableColumn<furniture, String> desc;
    public TableColumn<furniture, String> status;
    public TableView<furniture> assets;
    public TextField itemKey;
    public TextField name;
    public Pane furniture;
    public ObservableList<String> statuslist;
    public JFXDatePicker date;
    public TextField supplier;
    public TableColumn<furniture,String> SupplierCol;
    public TableColumn<furniture,String> dateCol;

    public static void main(String[] args) {

    }
    public void initialize(){
        DBCollection gigaCollection = DatabaseConnect.database("Furnitures");
        statuslist = FXCollections.observableArrayList();
        statuslist.add("New");
        statuslist.add("Beyond Repair");
        statuslist.add("Sold");

        key.setCellValueFactory(new PropertyValueFactory<>("Id"));
        desc.setCellValueFactory(new PropertyValueFactory<>("Name"));
        SupplierCol.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        status.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),statuslist));
        status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FurnitureAssets.furniture, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FurnitureAssets.furniture, String> event) {
                String id = assets.getSelectionModel().getSelectedItem().id;
                String newstatus = event.getNewValue();

                //DBCollection gigaCollection = DatabaseConnect.database("Furnitures");
                gigaCollection.update(new BasicDBObject("ID",id),new BasicDBObject("$set",new BasicDBObject("Status",newstatus)));
            }
        });
        desc.setCellFactory(TextFieldTableCell.forTableColumn());
        desc.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FurnitureAssets.furniture, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FurnitureAssets.furniture, String> event) {
                String id = assets.getSelectionModel().getSelectedItem().id;
                String newDesc = event.getNewValue();

                //DBCollection gigaCollection = DatabaseConnect.database("Furnitures");
                gigaCollection.update(new BasicDBObject("ID",id),new BasicDBObject("$set",new BasicDBObject("Name",newDesc)));
            }
        });
        assets.setEditable(true);
        Iterator<DBObject> fields = gigaCollection.find().iterator();

        assets.getItems().clear();

        while (fields.hasNext()) {

            BasicDBObject objects = (BasicDBObject) fields.next();
            Object Id = objects.get("ID");
            Object Name = objects.get("Name");
            Object sup = objects.get("Supplier");
            Object purchaseDate = objects.get("Date");
            Object FurnitureStatus = objects.get("Status");



            try {
                assets.getItems().add(new furniture(Id.toString(),Name.toString(),sup.toString(),purchaseDate.toString(),FurnitureStatus.toString()));
            }catch (NullPointerException e){

            }

        }

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Pane mainInterface = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        furniture.getChildren().setAll(mainInterface);
    }


    public void newFurniture(MouseEvent mouseEvent) {
        DBCollection gigaCollection = DatabaseConnect.database("Furnitures");
        //System.out.println(date.getValue());
        BasicDBObject details = new BasicDBObject();
        details.put("ID",itemKey.getText());
        details.put("Name", name.getText());
        details.put("Supplier", supplier.getText());
        details.put("Date", String.valueOf(date.getValue()));
        details.put("Status","New");
        gigaCollection.insert(details);
        initialize();
        itemKey.clear();
        name.clear();
    }

    public class furniture extends FurnitureAssets {
        String id,Name,Supplier,Date,Status;

         public furniture(String ID, String Name,String Supplier,String Date,String Status){
            this.id = ID;
            this.Name = Name;
            this.Supplier = Supplier;
            this.Date = Date;
            this.Status = Status;

        }
        public String getId() {
            return id;
        }

        public String getName() {
            return Name;
        }

        public String getSupplier() {
            return Supplier;
        }

        public String getDate() {
            return Date;
        }

        public String getStatus() {
            return Status;
        }
    }
}
