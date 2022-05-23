import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRegister{

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField fullNameField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextArea addressField;
    @FXML
    Label message;

    public void registerCustomer(){
        if(usernameField.getText().trim().isEmpty() ||
            passwordField.getText().trim().isEmpty() ||
            fullNameField.getText().trim().isEmpty() ||
            phoneNumberField.getText().trim().isEmpty() ||
            addressField.getText().trim().isEmpty()){

            message.setText("All fields must be filled in!");
        } else if(!phoneNumberField.getText().matches("[0-9]+")){
            message.setText("Phone number must be digits-only!");
        }else{
            ArrayList<String> info = new ArrayList<>();
            info.add(fullNameField.getText());
            info.add(phoneNumberField.getText());
            info.add(addressField.getText());
            Profile customer = new Profile(usernameField.getText(), passwordField.getText(),
                    2, false, info);
            ProfileDatabase.register(customer);
            message.setText("Registered as " + customer.getUsername());
            usernameField.clear();
            passwordField.clear();
            phoneNumberField.clear();
            addressField.clear();
            fullNameField.clear();
        }
    }


    public void gotoMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
