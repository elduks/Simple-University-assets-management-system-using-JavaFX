package functions;

import com.jfoenix.controls.JFXButton;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


/**
 * Created by Gigara
 */
public class Call {
    public TableView<calllog> maintainCall;
    public TableColumn<calllog, Integer> logNo;
    public TableColumn<calllog, String> desc;
    public TableColumn<calllog, String> status;
    public TextField logDescription;
    public JFXButton addbtn;
    public ObservableList<String> statuslist;
    public ImageView backarrow;
    public Pane maintainPane;

    public void initialize() {
        DBCollection gigaCollection = DatabaseConnect.database("Call");
        //gigaCollection.update(new BasicDBObject("ID",2),new BasicDBObject("$set",new BasicDBObject("Status","C")));
        statuslist = FXCollections.observableArrayList();
        statuslist.add("To Do");
        statuslist.add("Done");
        statuslist.add("Cancelled");

        logNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), statuslist));
        status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<calllog, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<calllog, String> event) {
                //System.out.println(event.getNewValue()+"  "+maintainCall.getSelectionModel().getSelectedItem().id);
                int logID = (maintainCall.getSelectionModel().getSelectedItem().id);
                String newstatus = event.getNewValue();

                DBCollection gigaCollection = DatabaseConnect.database("Call");
                gigaCollection.update(new BasicDBObject("ID",logID),new BasicDBObject("$set",new BasicDBObject("Status",newstatus)));

            }
        });


        Iterator<DBObject> fields = gigaCollection.find().iterator();

        maintainCall.getItems().clear();

        while (fields.hasNext()) {
            int count =0;
            BasicDBObject objects = (BasicDBObject) fields.next();
            Object logID = objects.get("ID");
            Object Desvalue = objects.get("Description");
            Object statusvalue = objects.get("Status");
            try {
                maintainCall.getItems().add(new calllog(Integer.parseInt(logID.toString()), Desvalue.toString(), statusvalue.toString()));
            }catch (NullPointerException e){

            }

        }

        maintainCall.setEditable(true);

        }

    public void newLog(MouseEvent mouseEvent) {

        DBCollection gigaCollection = DatabaseConnect.database("Call");
        long value = gigaCollection.count()+1;

        BasicDBObject Calldetails = new BasicDBObject();
        Calldetails.put("ID",value);
        Calldetails.put("Description", logDescription.getText());
        Calldetails.put("Status", "To Do");
        gigaCollection.insert(Calldetails);
        initialize();

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Pane mainInterface = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        maintainPane.getChildren().setAll(mainInterface);
    }


    public class calllog extends Call {
        int id;

        String description;
        String status;

        public calllog(int id, String description, String status) {
            this.id = id;
            this.description = description;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }
    }
}
