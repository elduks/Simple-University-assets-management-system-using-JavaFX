package login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import login.Main;
import login.user.users;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;



public class Controller {

    public Pane scene;
    public JFXTextField username;
    public JFXPasswordField password;
    public JFXProgressBar jfxBar;
    public ImageView close;

    public void initialize() {
        jfxBar.progressProperty().set(0);
    }

    public void signup(MouseEvent mouseEvent) throws IOException {
        Pane signuppane = FXMLLoader.load(getClass().getResource("user/signup.fxml"));

        scene.getChildren().setAll(signuppane);
    }

    public void changepsw(MouseEvent mouseEvent) throws IOException {
        Pane forgotpane = FXMLLoader.load(getClass().getResource("user/changepsw.fxml"));
        scene.getChildren().setAll(forgotpane);

    }
    public void interfaceo() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/interface.fxml")), 1000, 600);
        Stage interfacestage = new Stage();
        interfacestage.setTitle("Interface");
        interfacestage.setScene(scene);
        Main.primaryStage.close();

        interfacestage.show();
    }
    public void login(MouseEvent mouseEvent) throws IOException, InterruptedException {

        if(users.uservalid(username.getText(),password.getText())) {
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(0),
                            new KeyValue(jfxBar.progressProperty(), 0)),
                    new KeyFrame(
                            Duration.seconds(3),
                            new KeyValue(jfxBar.progressProperty(), 1)));
            timeline.play();
            timeline.setOnFinished(event -> {
                try {
                    interfaceo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        }else {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.initOwner(Main.primaryStage);
            err.setTitle("Error");
            err.setContentText("Username or Password Error");
            err.setHeaderText(null);
            err.showAndWait();
        }
    }

    public void close(MouseEvent mouseEvent) {
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(
                Duration.millis(200),
                new KeyValue (Main.primaryStage.getScene().getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> System.exit(0));
        timeline.play();
    }
}
