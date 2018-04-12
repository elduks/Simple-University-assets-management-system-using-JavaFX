package functions;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Gigara
 */
public class FloorPlan {
    public Pane floorplanpane;
    public ImageView backarrow;
    public ImageView floor1Plan;
    public JFXButton floorDetails;
    public static int a = 1;
    public static Stage newstage;

    public static void main(String[] args) {

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Pane mainInterface = FXMLLoader.load(getClass().getResource("/interface.fxml"));
        floorplanpane.getChildren().setAll(mainInterface);
    }

    public void floor1(MouseEvent mouseEvent) {
        Image floor1image = new Image("/res/floor-plan1.png");
        floor1Plan.setImage(floor1image);
        a = 1;
    }

    public void floor2(MouseEvent mouseEvent) {
        Image floor2image = new Image("/res/floor-plan2.jpeg");
        floor1Plan.setImage(floor2image);
        a = 2;
    }

    public void floorDetails(MouseEvent mouseEvent) throws IOException {
        Scene floordetails = new Scene(FXMLLoader.load(getClass().getResource("FloorDetails.fxml")),600,400);
        newstage = new Stage();
        newstage.setTitle("Floor Details");
        newstage.setScene(floordetails);
        newstage.show();
    }
}
