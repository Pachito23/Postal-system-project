import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Courier extends Profile{
    String vehicle_type;
    int max_capacity;
    int current_capacity;

    public Courier(String username, String password,int Profile_type, boolean is_being_read,ArrayList<String> personal_info)
    {
        super(username, password, Profile_type, is_being_read);
        this.vehicle_type=personal_info.get(0);
        this.max_capacity= Integer.valueOf(personal_info.get(1));
        this.current_capacity=Integer.valueOf(personal_info.get(2));
    }

    public Courier()
    {

    }

    public void check_assigned_deliveries()
    {
        ParcelDatabase.read_all();
        for(Parcel p:ParcelDatabase.database)
        {
            //make check deliveries check only the respective customer
            if(check_info(p)) {
                System.out.println("Order status: " + p.Order_Status);
                System.out.println("AWB: " + p.AWB);
                System.out.println("ETA: " + p.ETA);
                System.out.println("Size: " + p.Size);
                System.out.print("\tSender info: \n" + p.personal_info(p.Sender_info));
                System.out.println("\tRecipient info: \n" + p.personal_info(p.Recipient_info));
                System.out.println();
            }
        }
    }

    private boolean check_info(Parcel p)
    {
        if(p.Courier.equals(this.getUsername()) && p.Order_Status==1)
            return true;
        else
            return false;
    }

    public void delay_ETA(long AWB)
    {
        ParcelDatabase.read_all();
        Parcel p = ParcelDatabase.search_AWB(AWB);
        if(p!=null && p.AWB!=0 && check_info(p))
        {
            int delay=3;
            //delete the up initialization
            //input the amount of hours to delay <------------------
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime eta = LocalDateTime.parse(p.ETA,dtf);
            eta = eta.plusHours(delay);
            p.ETA = dtf.format(eta);
            ParcelDatabase.write_in_file();
            ParcelDatabase.database.clear();
        }
        else
        {
            if (AWB==0)
                System.out.println("The parcel was not assigned to any courier");
            else
            if(p==null || check_info(p)==false)
                System.out.println("Invalid parcel");
            else
                System.out.println("Error: AWB not found");
        }
        ParcelDatabase.database.clear();
    }

    public void Parcel_delivered(long AWB)
    {
        ParcelDatabase.read_all();
        Parcel p = ParcelDatabase.search_AWB(AWB);
        if(p!=null && p.AWB!=0 && check_info(p))
        {
            p.Order_Status=2;
            p.ETA="Delivered";
        }
        else
            System.out.println("Trouble finding the parcel");
        ParcelDatabase.write_in_file();
        ParcelDatabase.database.clear();
    }
}
