import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ControllerCustomer {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<Parcel> associatedParcels;
    private Customer loggedInAccount;

    @FXML
    TextField parcelSizeField;
    @FXML
    TextField recipientNameField;
    @FXML
    TextField recipientPhoneNumberField;
    @FXML
    TextArea recipientAddressField;
    @FXML
    Label loggedInAsLabel;
    @FXML
    Label parcelsAssociatedLabel;
    @FXML
    Label message;
    @FXML
    public void initialize(){
        loggedInAccount = (Customer)JavaFX.loggedInAccount;
        associatedParcels = loggedInAccount.get_deliveries();

        loggedInAsLabel.setText("Logged in as\n" + loggedInAccount.toString());
        if(associatedParcels.isEmpty())
            parcelsAssociatedLabel.setText("No associated parcels.");
        else
            parcelsAssociatedLabel.setText(associatedParcels.size() + " associated parcels.");
    }

    private void refreshParcels(){
        associatedParcels = loggedInAccount.get_deliveries();
        if(associatedParcels.isEmpty())
            parcelsAssociatedLabel.setText("No associated parcels.");
        else
            parcelsAssociatedLabel.setText(associatedParcels.size() + " associated parcels.");
    }

    public void sendParcel(ActionEvent event) throws IOException{

        if(!recipientAddressField.getText().trim().isEmpty() && !recipientNameField.getText().trim().isEmpty() &&
                !recipientPhoneNumberField.getText().trim().isEmpty() && !parcelSizeField.getText().trim().isEmpty()) {

            ArrayList<String> recipientData = new ArrayList<String>();
            recipientData.add(recipientNameField.getText());
            recipientData.add(recipientPhoneNumberField.getText());
            recipientData.add(recipientAddressField.getText());


            Parcel toSend = new Parcel(0, -1, "Not Set", "Not Set",
                    Integer.parseInt(parcelSizeField.getText()), loggedInAccount.personal_info , recipientData);
            ParcelDatabase.add_to_database(toSend);
            refreshParcels();
            message.setText("Parcel sent and awaiting confirmation!");
            parcelSizeField.clear();
            recipientNameField.clear();
            recipientPhoneNumberField.clear();
            recipientAddressField.clear();
        } else message.setText("All fields must be filled in!");

    }

    public void checkAssociatedParcels(ActionEvent event) throws IOException{
        if(!associatedParcels.isEmpty()){
            ControllerParcelSearch.setVisibility(2);
            ControllerParcelSearch.setToAdd(associatedParcels);
            ControllerParcelSearch.setLastScene("customer.fxml");
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
