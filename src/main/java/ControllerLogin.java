import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class ControllerLogin {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label message;

    public void login(ActionEvent event) throws IOException{
        if(!usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()){
            JavaFX.loggedInAccount = ProfileDatabase.login(usernameField.getText(), passwordField.getText());
            System.out.println(JavaFX.loggedInAccount);
        }
        else{
            message.setText("Username or password empty!");
            return;
        }

        if(JavaFX.loggedInAccount == null){
            message.setText("Invalid credentials!");
        }else switch (JavaFX.loggedInAccount.getProfile_type()) {
            case (2) -> gotoCustomer(event);
            case (1) -> gotoCourier(event);
            case (0) -> gotoManager(event);
            default -> exit(0);
        }
    }

    public void gotoMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("customer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void gotoCourier(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("courier.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void gotoManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
