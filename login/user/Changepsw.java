package login.user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Gigara
 */
public class Changepsw {
    public JFXTextField username;
    public JFXPasswordField password;
    public JFXPasswordField newPassword;
    public Pane changepswpane;

    public static void main(String[] args) {

    }

    public void chnagepsw(MouseEvent mouseEvent) {
        String x = users.newpsw(username.getText(),password.getText(),newPassword.getText());
        //JOptionPane.showMessageDialog(null,x,"Change Password",JOptionPane.INFORMATION_MESSAGE);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Pane signuppane = FXMLLoader.load(getClass().getResource("/login/sample.fxml"));
        changepswpane.getChildren().setAll(signuppane);
    }
}
