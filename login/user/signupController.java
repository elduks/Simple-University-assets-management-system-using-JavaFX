package login.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import login.Main;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Gigara
 */
public class signupController {
    public Pane signupp;
    public JFXPasswordField regPassword;
    public JFXTextField regUsername;
    public Pane signuppane;
    public JFXComboBox usertype;

    public void initialize() {
        usertype.getItems().add("Admin");
        usertype.getItems().add("Normal");

    }

    public void back(MouseEvent mouseEvent) throws IOException {

        signuppane = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        signupp.getChildren().setAll(signuppane);
    }

            public void register(MouseEvent mouseEvent) throws Exception {
                Alert msg = new Alert(Alert.AlertType.NONE);
                msg.initOwner(Main.primaryStage);
                msg.setHeaderText(null);
                if ((regUsername.getText().matches("")) || (regPassword.getText().matches(""))) {
                    msg.setTitle("Error");
                    msg.setAlertType(Alert.AlertType.ERROR);
                    msg.setContentText("Please enter a Username and Password");
                    msg.showAndWait();
                }else if (usertype.getSelectionModel().isEmpty()){
                    msg.setTitle("Error");
                    msg.setAlertType(Alert.AlertType.ERROR);
                    msg.setContentText("Please Select the user Type");
                    msg.showAndWait();

        } else {
            if (users.uservalid(regUsername.getText())) {
                msg.setTitle("Error");
                msg.setAlertType(Alert.AlertType.WARNING);
                msg.setContentText("Username already exist !");
                msg.showAndWait();

            } else {
                users.usercreate(regUsername.getText(), regPassword.getText(),usertype.getSelectionModel().getSelectedItem().toString());

                Alert conf = new Alert(Alert.AlertType.CONFIRMATION,"Sign In"+"OK",ButtonType.YES,ButtonType.CLOSE);
                conf.setContentText("User Successfully created\nDo you want to Log In ?");
                conf.setHeaderText(null);
                conf.initOwner(Main.primaryStage);
                conf.showAndWait();

                if(conf.getResult() == ButtonType.YES){

                    signupp.getScene().getWindow().hide();
                    Main.primaryStage.show();


                }
            }
        }

    }
}
