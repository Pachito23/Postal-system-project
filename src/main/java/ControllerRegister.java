import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRegister implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField keycodeField;

    @FXML
    private ChoiceBox<String> registerOptions;

    @FXML
    private Label keycodeLabel;

    @FXML
    private Label usernameLabel;

    private final String[] registerOps = {"Office Manager", "Courier", "Customer"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerOptions.getItems().addAll(registerOps);
    }

    public void checkValidRegistration(){
        usernameLabel.setText("");
        keycodeLabel.setText("");

        if(usernameField.getText().equals("monkey")){
            usernameLabel.setText("Username is already taken!");
        }
        if(!(keycodeField.getText().equals("admin"))){
            keycodeLabel.setText("Keycode invalid!");
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
