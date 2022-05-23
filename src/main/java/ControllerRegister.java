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

    @FXML Label message;

    private final String[] registerOps = {"Office Manager", "Courier", "Customer"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerOptions.getItems().addAll(registerOps);
    }

    public void checkValidRegistration(){
        Profile newProfile = null;
        String profileName = null,
               profilePassword = null,
               profileKeycode = null,
                option = null;

        try{
            option = registerOptions.getValue();
            profileName = usernameField.getText();
            profilePassword = passwordField.getText();
            if(option.equals("Office Manager") || option.equals("Courier"))
                profileKeycode = keycodeField.getText();
        } catch (RuntimeException e){
            message.setText("If registering as a manager or a courier, use a valid keycode. Otherwise, all fields must be completed.");
            return;
        }
        if(registerOptions.getValue() == "Office Manager" && keycodeField.getText().equals("admin")){
            newProfile = new Profile(usernameField.getText(), passwordField.getText(), 0, false);
        } else if(registerOptions.getValue().equals("Courier") && keycodeField.getText().equals("admin")){
            newProfile = new Profile(usernameField.getText(), passwordField.getText(), 1, false);
        } else if(registerOptions.getValue().equals("Customer"))
            newProfile = new Profile(usernameField.getText(), passwordField.getText(), 2, false);

        int registerResult = ProfileDatabase.register(newProfile);

        if(registerResult == -1)
            message.setText("Username already in use!");
        else if(registerResult == 0)
            message.setText("Successfully registered as " + usernameField.getText());

    }


    public void gotoMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
