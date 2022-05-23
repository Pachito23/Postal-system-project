import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Office_Manager extends Profile{
    public ArrayList<String> personal_info=new ArrayList<>(3);

    public Office_Manager(String username, String password,int Profile_type, boolean is_being_read,ArrayList<String> personal_info)
    {
        super(username, password, Profile_type, is_being_read);
        this.personal_info=personal_info;
    }

    public Office_Manager()
    {

    }

    // Accept/Deny functionality was moved to ControllerParcelSearch

//    public void Accept_Deny_delivery(Parcel toManage){
//        ParcelDatabase.read_all();
//        int counter=1;
//        for(Parcel parcel:ParcelDatabase.database)
//        {
//            if(parcel.Order_Status==0) {
//                System.out.println("Number: " + counter + "\n"+ parcel);
//                counter++;
//            }
//        }
//
//        if (counter==1)
//        {
//            System.out.println("No parcels to approve or deny");
//            return;
//        }

        //read integer number to select (1...n) the entry and true or false to approve or not <-----------------------
        //delete the down initializations

//        for(Parcel parcel:ParcelDatabase.database)
//        {
//            if(parcel.Order_Status==0)
//                counter2++;
//            if(parcel.Order_Status==0 && counter2==selected)
//                if(approved) {
//                    String courier_username="Courier";
//                    //delete the up initialization
//                    //ask to input courier username <------------------------------------
//                    if(assign_to_courier(parcel,courier_username))
//                        parcel.Order_Status = 1;
//                }
//                else
//                    parcel.Order_Status=-1;
//        }
//
//        if(selected>counter)
//            System.out.println("The selected input is too big, the allowed interval is from 1 to " + counter);
//
//        ParcelDatabase.update_database();
//    }

    // Functionality also moved to ControllerParcelSearch

//    public boolean assign_to_courier(Parcel parcel, String courier_username)
//    {
//        if(ProfileDatabase.courier_exists(courier_username) &&
//           ProfileDatabase.Add_cargo_courier(courier_username, parcel.Size)) {
//            Random rand = new Random(); //instance of random class
//            int upperbound = 168; //one week maximum
//            int lowerbound=24; //one day minimum
//            long hours = rand.nextInt(lowerbound, upperbound); //hours in which is estimated to arrive the package
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            now=now.plusHours(hours);
//            parcel.ETA=dtf.format(now);
//            parcel.Courier = courier_username;
//            parcel.AWB=ParcelDatabase.AWB_counter;
//            ParcelDatabase.AWB_counter++;
//            return true;
//        }
//        /*else
//        {
//            System.out.println(courier_username + " does not exists bla bla bla something");
//            ask new input of courier_username or exit <---------------------------------------
//            return false;
//        }
//         */
//        System.out.println(courier_username + " does not exists");
//        return false;
//    }

    public void see_all_deliveries_data()
    {
        ParcelDatabase.read_all();
        ParcelDatabase.print_database();
        ParcelDatabase.database.clear();
    }

    @Override
    public String toString() {
        return "Username: " + super.toString() +
                "Name: " + personal_info.get(0) +
                "Office Phone Number: " + personal_info.get(1) +
                "Office Address: " + personal_info.get(2);
    }
}
