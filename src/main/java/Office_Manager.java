import java.util.ArrayList;

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

    public void Accept_Deny_delivery(){
        ParcelDatabase.read_all();
        int counter=1;
        for(Parcel parcel:ParcelDatabase.database)
        {
            if(parcel.Order_Status==0) {
                System.out.println("Number: " + counter + "\n"+ parcel);
                counter++;
            }
        }

        if (counter==1)
        {
            System.out.println("No parcels to approve or deny");
            return;
        }

        //read integer number to select the entry and true or false to approve or not <-----------------------
        int selected=1;
        boolean approved=false;

        int counter2=0;
        for(Parcel parcel:ParcelDatabase.database)
        {
            if(parcel.Order_Status==0)
                counter2++;
            if(parcel.Order_Status==0 && counter2==selected)
                if(approved)
                    parcel.Order_Status=1;
                else
                    parcel.Order_Status=-1;
        }

        if(selected>counter)
            System.out.println("The selected input is too big, the allowed interval is from 1 to " + counter);

        ParcelDatabase.update_database();
    }


}
