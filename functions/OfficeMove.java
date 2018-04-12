package functions;

import com.jfoenix.controls.JFXDatePicker;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.util.converter.DefaultStringConverter;
import login.Main;
import login.user.users;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Gigara
 */
public class OfficeMove {
    public Pane officemovepane;
    public TextField room;
    public TextField moveTo;
    public TableColumn<movellog,String> To;
    public TableColumn<movellog,String> Request;
    public TableColumn<movellog,String> From;
    public TableColumn<movellog,String> Time;
    public TableColumn<movellog,String> Description;
    public TableColumn<movellog,String> Scheduled;
    public TableColumn<movellog,String> Status;
    public TextField description;
    public TableView<movellog> movetable;
    public JFXDatePicker scheduledate;
    public ObservableList<String> statuslist;

    public static void main(String[] args) {

    }

    public void back(MouseEvent mouseEvent) throws IOException {

        Pane mainInterface = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        officemovepane.getChildren().setAll(mainInterface);
    }

    public void initialize(){
        DBCollection gigaCollection = DatabaseConnect.database("OfficeMove");
        statuslist = FXCollections.observableArrayList();
        statuslist.add("Scheduled");
        statuslist.add("Requested");
        statuslist.add("Cancelled");
        statuslist.add("Completed");

        From.setCellValueFactory(new PropertyValueFactory<>("From"));
        To.setCellValueFactory(new PropertyValueFactory<>("To"));
        Request.setCellValueFactory(new PropertyValueFactory<>("Requested"));
        Time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Scheduled.setCellValueFactory(new PropertyValueFactory<>("Schedule"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));

        Status.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), statuslist));
        movetable.setEditable(true);
        Status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<movellog, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<movellog, String> event) {
                int logID = (movetable.getSelectionModel().getSelectedItem().id);
                String newstatus = event.getNewValue();

                System.out.println(logID);

                DBCollection gigaCollection = DatabaseConnect.database("OfficeMove");
                gigaCollection.update(new BasicDBObject("ID",logID),new BasicDBObject("$set",new BasicDBObject("Status",newstatus)));
            }
        });

        Iterator<DBObject> fields = gigaCollection.find().iterator();


        movetable.getItems().clear();

        while (fields.hasNext()) {

            BasicDBObject objects = (BasicDBObject) fields.next();
            Object Id = objects.get("ID");
            Object from = objects.get("From");
            Object to = objects.get("To");
            Object requested = objects.get("Requested");
            Object time = objects.get("Time");
            Object description = objects.get("Description");
            Object schedule = objects.get("Schedule");
            Object status = objects.get("Status");



            try {
                movetable.getItems().add(new OfficeMove.movellog(Integer.parseInt(Id.toString()),from.toString(),to.toString(),requested.toString()
                        ,time.toString(),description.toString(),schedule.toString(),status.toString()));
            }catch (NullPointerException e){

            }

        }
    }

    public void add(MouseEvent mouseEvent) {
        DBCollection gigaCollection = DatabaseConnect.database("OfficeMove");

        if(room.getText().equals("")||moveTo.getText().equals("")||description.getText().equals("")){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.initOwner(Main.primaryStage);
            err.setTitle("Error");
            err.setContentText("Please Fill Fields");
            err.setHeaderText(null);
            err.showAndWait();
        }else {

            long value = gigaCollection.count() + 1;
            BasicDBObject details = new BasicDBObject();
            details.put("ID", value);
            details.put("From", room.getText());
            details.put("To", moveTo.getText());
            details.put("Requested", users.loggeduser);
            details.put("Time", (new java.util.Date()));
            details.put("Description", description.getText());
            details.put("Schedule", String.valueOf(scheduledate.getValue()));
            details.put("Status", "Requested");
            gigaCollection.insert(details);
            initialize();
        }
    }

    public class movellog extends OfficeMove {


        String description,From,To,Requested,time,Schedule,status;
        int id;


        public movellog(int id,String From,String To,String Requested,String time,String description,String Schedule, String status) {

            this.description = description;
            this.status = status;
            this.From = From;
            this.To = To;
            this.Requested = Requested;
            this.time = time;
            this.Schedule = Schedule;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getFrom() {
            return From;
        }

        public String getTo() {
            return To;
        }

        public String getRequested() {
            return Requested;
        }

        public String getTime() {
            return time;
        }

        public String getSchedule() {
            return Schedule;
        }

        public String getStatus() {
            return status;
        }
    }
}
