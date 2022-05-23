import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerManager {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<Parcel> unassignedParcels = new ArrayList<>();
    private String[] options = {"Office Manager", "Courier"};
    private Office_Manager loggedInAccount;

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
    public void initialize(){
        accountOption.getItems().addAll(options);
        ParcelDatabase.read_all();
        loggedInAccount = (Office_Manager)JavaFX.loggedInAccount;

        loggedInAsLabel.setText("Logged in as\n" + loggedInAccount.toString());
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
