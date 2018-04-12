package login;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/login/sample.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setAlwaysOnTop(true);
        FadeTransition st = new FadeTransition(Duration.seconds(4),root);
        st.setFromValue(0);
        st.setToValue(1);
        st.play();
        primaryStage.show();

    }


    public static void main(String[] args) {

        //users.usercreate("admin","admin");


        launch(args);
    }
}
