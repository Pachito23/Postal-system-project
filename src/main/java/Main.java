import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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

        Profile admin = ProfileDatabase.login("Admin","Admin");

        Profile profile1 = new Profile("Customer","123",2,false,info1);
        Profile profile2 = new Profile("Courier","789",1,false,courier_data);
        Profile profile3 = new Profile("Office Manager","abcisme",0,false,info3);
        admin.register(profile1);
        admin.register(profile2);
        admin.register(profile3);
        ProfileDatabase.print();

        Parcel p1= new Parcel(0,0,"Not set","Not set",10, info1, info1);
        Parcel p2= new Parcel(0,1,"Not set","Not set",20, info2, info2);
        ParcelDatabase.add_to_database(p1);
        ParcelDatabase.add_to_database(p2);
        ParcelDatabase.print_database();

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
