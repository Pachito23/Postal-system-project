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

        Parcel p1= new Parcel(0,0,"Not set",0,0, info1, info1);
        Parcel p2= new Parcel(0,1,"Not set",0,0, info2, info2);
        //ParcelDatabase.add_to_database(p1);
        //ParcelDatabase.add_to_database(p2);
        ParcelDatabase.print_database();

        Profile profile1 = new Profile("Guy1","123",2,false,info1);
        Profile profile2 = new Profile("Guy2","789",1,false,info2);
        //ProfileDatabase.register(profile1);
        //ProfileDatabase.register(profile2);
        Profile aux = ProfileDatabase.login("Guy1","123");
        Customer c;
        if(aux.getProfile_type()==2)
            c = (Customer) aux;
    }
}
