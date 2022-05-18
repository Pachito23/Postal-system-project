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

        Parcel p1= new Parcel(0,0,"Not set",0,0, info1, info1);
        Parcel p2= new Parcel(0,1,"Not set",0,0, info2, info2);
        ParcelDatabase.add_to_database(p1);
        ParcelDatabase.add_to_database(p2);
        //ParcelDatabase.print_database();

        Profile profile1 = new Profile("Customer","123",2,false,info1);
        Profile profile2 = new Profile("Courier","789",1,false,info2);
        Profile profile3 = new Profile("Office Manager","abcisme",0,false,info3);
        //ProfileDatabase.register(profile1);
        //ProfileDatabase.register(profile2);
        //ProfileDatabase.register(profile3);
        //ProfileDatabase.print();

        Profile temp = ProfileDatabase.login("Customer","123");
        Office_Manager o = new Office_Manager();
        Customer c = new Customer();
        if(temp.getProfile_type()==0)
            o = (Office_Manager) temp;
        if(temp.getProfile_type()==2)
            c = (Customer) temp;



        /*
        ParcelDatabase.read_all();
        ParcelDatabase.print_database();
        ParcelDatabase.database.clear();
        */
    }
}
