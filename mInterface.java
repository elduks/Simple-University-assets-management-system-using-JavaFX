import com.jfoenix.controls.JFXComboBox;
import functions.FMO;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.Main;
import login.user.users;
import java.io.*;


/**
 * Created by Gigara
 */
public class mInterface {
    public JFXComboBox dropd;
    public Pane minterface;
    public static Stage FMOStage;

    public void initialize() throws IOException {

        dropd.getItems().addAll("Log Out");
        dropd.promptTextProperty().set(users.loggeduser);
    }


    public static void main(String[] args) {


    }

    public void logout(ActionEvent actionEvent) throws IOException {

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Main.primaryStage.show();

    }


    public void call(MouseEvent mouseEvent) throws IOException {
        Pane callpane = FXMLLoader.load(getClass().getResource("functions/Call.fxml"));
        minterface.getChildren().setAll(callpane);
    }


    public void officemove(MouseEvent mouseEvent) throws IOException {
        Pane callpane = FXMLLoader.load(getClass().getResource("functions/OfficeMove.fxml"));
        minterface.getChildren().setAll(callpane);
    }

    public void floorPlan(MouseEvent mouseEvent) throws IOException {
        if (users.loggeduserType.equals("Admin")) {
            Pane callpane = FXMLLoader.load(getClass().getResource("functions/FloorPlan.fxml"));
            minterface.getChildren().setAll(callpane);
        } else {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.initOwner(Main.primaryStage);
            err.setTitle("Error");
            err.setContentText("You Don't Have Access to this function");
            err.setHeaderText(null);
            err.showAndWait();
        }
    }

    public void FMO(MouseEvent mouseEvent) throws Exception {
        FMO.initialize();

    }

    public void reallocation(MouseEvent mouseEvent) throws IOException {
        Pane callpane = FXMLLoader.load(getClass().getResource("functions/Call.fxml"));
        minterface.getChildren().setAll(callpane);
    }

    public void assets(MouseEvent mouseEvent) throws IOException {
        Pane assets = FXMLLoader.load(getClass().getResource("functions/FurnitureAssets.fxml"));
        minterface.getChildren().setAll(assets);
    }

    public void newUser(MouseEvent mouseEvent) throws IOException {

        if (users.loggeduserType.equals("Admin")) {
            Pane signuppane = FXMLLoader.load(getClass().getResource("login/user/signup.fxml"));

            minterface.getChildren().setAll(signuppane);
        } else {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.initOwner(Main.primaryStage);
            err.setTitle("Error");
            err.setContentText("You Don't Have Access to this function");
            err.setHeaderText(null);
            err.showAndWait();
        }
    }



    }
