import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMainMenu {


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField awb;
    @FXML
    Label message;

    public void searchAWB(ActionEvent event) throws IOException {

        if(awb.getText().trim().isEmpty() || !awb.getText().matches("[0-9]+")){
            message.setText("Input a digits-only AWB!");
        } else {
            ArrayList<Parcel> parcels = new ArrayList<Parcel>();
            int AWBToSearch = Integer.parseInt(awb.getText());
            ParcelDatabase.read_all();
            for (Parcel item : ParcelDatabase.database) {
                if (item.AWB == AWBToSearch)
                    parcels.add(item);
            }
            ParcelDatabase.database.clear();
            if (parcels.isEmpty()) {
                message.setText("No parcel found.");

            } else {
                ControllerParcelSearch.setToAdd(parcels);
                gotoParcelSearch(event);
            }
        }

    }

    public void gotoRegister(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoParcelSearch(ActionEvent event) throws IOException {
        ControllerParcelSearch.setVisibility(2);
        ControllerParcelSearch.setLastScene("mainmenu.fxml");
        root = FXMLLoader.load(getClass().getResource("parcelsearch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
