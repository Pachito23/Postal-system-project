import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class ControllerParcelSearch {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private static ArrayList<Parcel> toAdd;
    private static String lastScene;
    private static int visibility = 2;
    private int courierWeight;

    @FXML
    public TableView<Parcel> parcelView;
    public TableColumn<Parcel, Integer> Order_Status;
    public TableColumn<Parcel, Long> AWB;
    public TableColumn<Parcel, String> Courier;
    public TableColumn<Parcel, String> ETA;
    public TableColumn<Parcel, Integer> Size;
    public TableColumn<Parcel, ArrayList<String>> Sender_info;
    public TableColumn<Parcel, ArrayList<String>> Recipient_info;

    @FXML
    public Button delayPackageButton;
    @FXML
    public Button markDeliveredButton;
    @FXML
    public Button assignButton;
    @FXML
    public Button  rejectButton;
    @FXML
    public TextField hoursToDelayField;
    @FXML
    public ComboBox<Profile> selectedCourier;
    @FXML
    public Label messageManagerLabel;

    @FXML
    public void initialize(){

        switch (visibility) {
            case (0) -> {
                delayPackageButton.setVisible(false);
                markDeliveredButton.setVisible(false);
                assignButton.setVisible(true);
                rejectButton.setVisible(true);
                selectedCourier.setVisible(true);
                hoursToDelayField.setVisible(false);
                ArrayList<Profile> allCouriers = ProfileDatabase.retrieveAllOfAType(1);
                selectedCourier.getItems().addAll(allCouriers);
            }
            case (1) -> {
                delayPackageButton.setVisible(true);
                markDeliveredButton.setVisible(true);
                assignButton.setVisible(false);
                rejectButton.setVisible(false);
                selectedCourier.setVisible(false);
                hoursToDelayField.setVisible(true);
            }
            default -> {
                delayPackageButton.setVisible(false);
                markDeliveredButton.setVisible(false);
                assignButton.setVisible(false);
                rejectButton.setVisible(false);
                selectedCourier.setVisible(false);
                hoursToDelayField.setVisible(false);
            }
        }

        Order_Status.setCellValueFactory(new PropertyValueFactory<>("Order_Status"));
        AWB.setCellValueFactory(new PropertyValueFactory<>("AWB"));
        Courier.setCellValueFactory(new PropertyValueFactory<>("Courier"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        Size.setCellValueFactory(new PropertyValueFactory<>("Size"));
        Sender_info.setCellValueFactory(new PropertyValueFactory<>("Sender_info"));
        Recipient_info.setCellValueFactory(new PropertyValueFactory<>("Recipient_info"));

        parcelView.getItems().clear();

        toAdd.forEach((item) -> parcelView.getItems().add(item));

    }

    public void deliverParcel(ActionEvent event) throws IOException{
        if(parcelView.getSelectionModel().getSelectedItem() != null){
            Courier loggedInAccount = (Courier)JavaFX.loggedInAccount;
            Parcel toDeliver = parcelView.getSelectionModel().getSelectedItem();
            loggedInAccount.Parcel_delivered(toDeliver);
            toAdd.remove(toDeliver);
            parcelView.getItems().remove(toDeliver);
            parcelView.refresh();
        }
    }


    public void delayParcel(ActionEvent event) throws IOException{
        if(!hoursToDelayField.getText().trim().isEmpty()){
            Courier loggedInAccount = (Courier)JavaFX.loggedInAccount;

            Parcel toDelay = parcelView.getSelectionModel().getSelectedItem();
            loggedInAccount.delay_ETA_by_instance(toDelay, Integer.parseInt(hoursToDelayField.getText()));
            parcelView.refresh();
        }
    }

    public void rejectParcel(ActionEvent event) throws IOException{
        Parcel toDelete = parcelView.getSelectionModel().getSelectedItem();
        ParcelDatabase.delete_from_database_by_instance(toDelete);
        toAdd.remove(toDelete);
        parcelView.getItems().remove(toDelete);
        parcelView.refresh();
    }

    public void Accept_delivery(ActionEvent event) throws IOException{
        if(selectedCourier.getValue() != null && parcelView.getSelectionModel().getSelectedItem() != null
                && parcelView.getSelectionModel().getSelectedItem().getOrder_Status()==0){
            Parcel toManage = parcelView.getSelectionModel().getSelectedItem();
            if(assign_to_courier(toManage, selectedCourier.getValue().getUsername())) {
                    parcelView.getItems().remove(toManage);
                    courierWeight -= toManage.Size;
                    messageManagerLabel.setText("Free capacity: " + courierWeight);
            }
                ParcelDatabase.update_database();
                parcelView.refresh();
        } else
        messageManagerLabel.setText("Select a courier first!");
    }

    public void displaySelectedCourierCapacity(ActionEvent event) throws IOException{
        if(selectedCourier.getValue() != null){
            courierWeight = Integer.parseInt(selectedCourier.getValue().information.get(1)) -
                    Integer.parseInt(selectedCourier.getValue().information.get(2));
            messageManagerLabel.setText("Free capacity: " + courierWeight);
        }
    }

    public boolean assign_to_courier(Parcel parcel, String courier_username) {
        if (ProfileDatabase.courier_exists(courier_username) &&
                Add_cargo_courier(courier_username, parcel.Size)) {
            Random rand = new Random(); //instance of random class
            int upperbound = 168; //one week maximum
            int lowerbound = 24; //one day minimum
            long hours = rand.nextInt(lowerbound, upperbound); //hours in which is estimated to arrive the package
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            now = now.plusHours(hours);
            parcel.ETA = dtf.format(now);
            parcel.Courier = courier_username;
            parcel.AWB = ParcelDatabase.AWB_counter;
            parcel.Order_Status = 1;
            ParcelDatabase.AWB_counter++;
            return true;
        }
        return false;
    }

    public static boolean Add_cargo_courier(String username,int cargo_to_add)
    {
        ProfileDatabase.read_all();
        for(Profile p: ProfileDatabase.database)
        {
            if(p.getUsername().equals(username) && p.getProfile_type()==1)
            {
                int new_size = Integer.parseInt(p.information.get(2))+cargo_to_add;
                if(new_size>Integer.parseInt(p.information.get(1)))
                {
                    return false;
                }
                p.information.remove(2);
                p.information.add(2,Integer.toString(new_size));
            }
        }
        ProfileDatabase.write_in_file();
        ProfileDatabase.database.clear();
        return true;
    }

    public void gotoLastScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(lastScene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void setToAdd(ArrayList<Parcel> toAdd) {
        ControllerParcelSearch.toAdd = toAdd;
    }

    public static void setLastScene(String lastScene) {
        ControllerParcelSearch.lastScene = lastScene;
    }

    public static void setVisibility(int visibility) {
        ControllerParcelSearch.visibility = visibility;
    }

}
