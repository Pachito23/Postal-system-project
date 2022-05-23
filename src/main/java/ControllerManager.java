import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerManager implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<Parcel> unassignedParcels = new ArrayList<>();
    private String[] options = {"Office Manager", "Courier"};
    private Office_Manager loggedInAccount;

    @FXML
    TextField usernameRegisterField;
    @FXML
    PasswordField passwordRegisterField;
    @FXML
    TextField RegisterField1;
    @FXML
    TextField RegisterField2;
    @FXML
    TextField RegisterField3;
    @FXML
    Label loggedInAsLabel;
    @FXML
    Label parcelsMessage;
    @FXML
    Label message;
    @FXML
    ComboBox<String> accountOption;

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        accountOption.getItems().addAll(options);
        ParcelDatabase.read_all();
        loggedInAccount = (Office_Manager)JavaFX.loggedInAccount;


//        loggedInAsLabel.setText("Logged in as\n" + loggedInAccount.toString());


        if(ParcelDatabase.database.isEmpty())
            parcelsMessage.setText("No parcels in database.");
        else{
            for(Parcel p: ParcelDatabase.database){
                if(p.Order_Status == 0){
                    unassignedParcels.add(p);
                }
            }
            parcelsMessage.setText(ParcelDatabase.database.size() + " parcels in database, out of which " +
                                    unassignedParcels.size() + " are unassigned.");
        }
    }

    public void registerAccount(){
        Profile toRegister;
        if(usernameRegisterField.getText().trim().isEmpty() ||
            passwordRegisterField.getText().trim().isEmpty() ||
            RegisterField1.getText().trim().isEmpty() ||
            RegisterField2.getText().trim().isEmpty() ||
            (RegisterField3.getText().trim().isEmpty() && accountOption.getValue().equals("Office Manager"))){

            message.setText("All fields must be filled in!");
        } else if(accountOption.getValue().equals("Office Manager") && !RegisterField2.getText().matches("[0-9]+")){
            message.setText("Phone number must be digits-only!");
        }else if(accountOption.getValue().equals("Courier") && !RegisterField2.getText().matches("[0-9]+")){
            message.setText("Vehicle capacity must be a number!");
        }else{
            ArrayList<String> info = new ArrayList<>();
            info.add(RegisterField1.getText());
            info.add(RegisterField2.getText());
            if(accountOption.getValue().equals("Courier"))
                info.add("0");
            else
                info.add(RegisterField3.getText());
            if(accountOption.getValue().equals("Office Manager"))
                toRegister = new Profile(usernameRegisterField.getText(),
                        passwordRegisterField.getText(),0, false, info);
            else
                toRegister = new Profile(usernameRegisterField.getText(),
                        passwordRegisterField.getText(),1, false, info);
            ProfileDatabase.register(toRegister);

            message.setText("Registered as " + usernameRegisterField.getText());
            usernameRegisterField.clear();
            passwordRegisterField.clear();
            RegisterField1.clear();
            RegisterField2.clear();
            RegisterField3.clear();
        }
    }

    public void setPrompts(ActionEvent event){
        if(accountOption.getValue().equals("Office Manager")){
            RegisterField1.setPromptText("Manager Full Name");
            RegisterField2.setPromptText("Office Phone Number");
            RegisterField3.setPromptText("Office Address");
            RegisterField3.setVisible(true);

        }else if(accountOption.getValue().equals("Courier")){
            RegisterField1.setPromptText("Vehicle Type");
            RegisterField2.setPromptText("Vehicle Capacity");
            RegisterField3.setVisible(false);
        }
    }

    public void manageUnassignedParcels(ActionEvent event) throws IOException{
        if(!unassignedParcels.isEmpty()){
            ControllerParcelSearch.setVisibility(0);
            ControllerParcelSearch.setToAdd(unassignedParcels);
            ControllerParcelSearch.setLastScene("manager.fxml");
            root = FXMLLoader.load(getClass().getResource("parcelsearch.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void seeAllParcels(ActionEvent event) throws IOException{
        if(!ParcelDatabase.database.isEmpty()){
            ControllerParcelSearch.setVisibility(2);
            ControllerParcelSearch.setToAdd(ParcelDatabase.database);
            ControllerParcelSearch.setLastScene("manager.fxml");
            root = FXMLLoader.load(getClass().getResource("parcelsearch.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void logOutToMainMenu(ActionEvent event) throws IOException {
        JavaFX.loggedInAccount = null;
        this.loggedInAccount = null;
        root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
