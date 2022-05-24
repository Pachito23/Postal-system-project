
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class Automate_testing {

    static void start_up()
    {
        ParcelDatabase.empty();
        ProfileDatabase.empty();

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

        Parcel p1= new Parcel(0,0,"Not set","Not set",10, info1, info1);
        Parcel p2= new Parcel(0,120,"Not set","Not set",20, info2, info2);
        ParcelDatabase.add_to_database(p1);
        ParcelDatabase.add_to_database(p2);
    }

    //login as admin with empty database
    @Test
    void login_admin(){
        ProfileDatabase.empty();
        Profile log_test = ProfileDatabase.login("Admin","Admin");
        if(log_test!=null)
            System.out.println("Test 1 passed");
        else
            System.out.println("Test 1 failed");
    }

    //getting rid of the disposable profile
    @Test
    void register_admin(){
        ProfileDatabase.empty();
        Profile log_test = ProfileDatabase.login("Admin","Admin");
        ArrayList<String> info3 = new ArrayList<>();
        info3.add("Alupigus tlum");
        info3.add("666");
        info3.add("Back Gateway");
        Profile profile = new Profile("Office Manager","abcisme",0,false,info3);
        log_test.register(profile);
        log_test = ProfileDatabase.login("Office Manager","abcisme");
        Profile log_test2 = ProfileDatabase.login("Admin","Admin");
        if(log_test!=null && log_test2==null)
            System.out.println("Test 2 passed");
        else
            System.out.println("Test 2 failed");
    }

    @Test
    void profile_create()
    {
        boolean ok=false;
        Profile profile1 = new Profile("Customer","123",2,false);
        Profile profile2 = new Profile("Courier","789",1,false);
        Profile profile3 = new Profile("Office Manager","abcisme",0,false);
        if(profile1!=null && !profile1.getPassword().equals("123"))
            if(profile2!=null && !profile2.getPassword().equals("789"))
                if(profile3!=null && !profile3.getPassword().equals("abcisme"))
                    ok=true;
        if(ok)
            System.out.println("Test 3 passed");
        else
            System.out.println("Test 3 failed");
    }

    @Test
    void create_parcel()
    {
        ArrayList<String> info = new ArrayList<>();
        Parcel p= new Parcel(0,0,"Test","TEST",10, info, info);
        if(p!=null && p.Courier.equals("Test") && p.ETA.equals("TEST"))
            System.out.println("Test 4 passed");
        else
            System.out.println("Test 4 failed");
    }

    @Test
    void check_by_AWB()
    {
        ParcelDatabase.empty();
        start_up();
        Parcel test1 = ParcelDatabase.search_AWB(120);
        Parcel test2 = ParcelDatabase.search_AWB(52);
        if(test1!=null && test2==null)
            System.out.println("Test 5 passed");
        else
            System.out.println("Test 5 failed");
    }

    @Test
    void send_parcel()
    {
        start_up();
        ParcelDatabase.empty();
        Customer customer = (Customer) ProfileDatabase.login("Customer","123");
        customer.Send_Parcel(10,"Recipient_name","Recipient_phone","Recipient_address");
        if(!ParcelDatabase.database.isEmpty())
            System.out.println("Test 6 passed");
        else
            System.out.println("Test 6 failed");
    }

    @Test
    void deliver_parcel()
    {
        start_up();
        ParcelDatabase.empty();
        ArrayList<String> info2 = new ArrayList<>();
        info2.add("Hugh Jass");
        info2.add("69");
        info2.add("Oral Plaza");
        Parcel p= new Parcel(1,120,"Courier","28/05/2022 05:40:00",20, info2, info2);
        ParcelDatabase.add_to_database(p);
        Courier courier = (Courier) ProfileDatabase.login("Courier","789");
        courier.Parcel_delivered(ParcelDatabase.search_AWB(120));
        if(ParcelDatabase.search_AWB(120).Order_Status==2)
            System.out.println("Test 7 passed");
        else
            System.out.println("Test 7 failed");
    }

    @Test
    void delay()
    {
        start_up();
        ParcelDatabase.empty();
        Courier courier = (Courier) ProfileDatabase.login("Courier","789");
        long AWB=120;
        ArrayList<String> info2 = new ArrayList<>();
        info2.add("Hugh Jass");
        info2.add("69");
        info2.add("Oral Plaza");
        Parcel p= new Parcel(1,120,"Courier","28/05/2022 05:40:00",20, info2, info2);
        ParcelDatabase.add_to_database(p);
        String initial_ETA = ParcelDatabase.search_AWB(AWB).ETA;
        courier.delay_ETA(AWB);
        String final_ETA = ParcelDatabase.search_AWB(AWB).ETA;
        if(!initial_ETA.equals(final_ETA))
            System.out.println("Test 8 passed");
        else
            System.out.println("Test 8 failed");
    }

    @AfterEach
    void reset()
    {
        ParcelDatabase.empty();
        ProfileDatabase.empty();
    }
}
