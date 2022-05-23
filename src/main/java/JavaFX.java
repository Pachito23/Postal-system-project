import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;



public class JavaFX extends Application {

    public static Profile loggedInAccount;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> info1 = new ArrayList<>();
        info1.add("Ben Dover");
        info1.add("420");
        info1.add("Penez Street");

        ArrayList<String> info2 = new ArrayList<>();
        info2.add("Hugh Jass");
        info2.add("69");
        info2.add("Oral Plaza");

        ArrayList<String> info3 = new ArrayList<>();
        info3.add("Alupigus tlum");
        info3.add("666");
        info3.add("Back Gateway");

        ArrayList<String> courier_data = new ArrayList<>();
        courier_data.add("Car");
        courier_data.add("100");
        courier_data.add("5");

        Profile profile1 = new Profile("Customer","123",2,false,info1);
        Profile profile2 = new Profile("Courier","789",1,false,courier_data);
        Profile profile3 = new Profile("Office Manager","abcisme",0,false,info3);
//        ProfileDatabase.register(profile1);
//        ProfileDatabase.register(profile2);
//        ProfileDatabase.register(profile3);

        Profile temp = ProfileDatabase.login("Courier","789");
        Office_Manager office_manager = new Office_Manager();
        Courier courier = new Courier();
        Customer customer = new Customer();
        if(temp!=null && temp.getProfile_type()==0)
            office_manager = (Office_Manager) temp;
        if(temp!=null && temp.getProfile_type()==1)
            courier = (Courier) temp;
        if(temp!=null && temp.getProfile_type()==2)
            customer = (Customer) temp;

        /*ParcelDatabase.read_all();
        ParcelDatabase.print_database();
        ParcelDatabase.database.clear();*/
    }

}