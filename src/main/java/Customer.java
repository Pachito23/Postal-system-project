import java.util.ArrayList;

public class Customer extends Profile{

    public ArrayList<String> personal_info=new ArrayList<>(3);

    public Customer(String username, String password,int Profile_type, boolean is_being_read,ArrayList<String> personal_info)
    {
        super(username, password, Profile_type, is_being_read);
        this.personal_info=personal_info;
    }

    public Customer()
    {

    }

    private boolean check_info(ArrayList<String> to_compare)
    {
        if(this.personal_info.get(0).equals(to_compare.get(0)))
            if(this.personal_info.get(1).equals(to_compare.get(1)))
                if(this.personal_info.get(2).equals(to_compare.get(2)))
            return true;

        return false;
    }

    public void check_deliveries()
    {
        ParcelDatabase.read_all();
        for(Parcel p:ParcelDatabase.database)
        {
            //make check deliveries check only the respective customer
            if(check_info(p.Sender_info)) {
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

    public void Send_Parcel(int Size,String Recipient_FullName, String Recipient_PhoneNumber, String Recipient_Address)
    {
        if(Size>100)
        {
            System.out.println("Size too big, maximum size is 100");
            System.out.println("Please enter new size");
            //enter a new size <-----------------------------------------------
        }
        else
        {
            ArrayList<String> recipient_info=new ArrayList<>(3);
            recipient_info.add(Recipient_FullName);
            recipient_info.add(Recipient_PhoneNumber);
            recipient_info.add(Recipient_Address);
            Parcel p = new Parcel(0,0,"Not set",0,Size,personal_info,recipient_info);
            ParcelDatabase.add_to_database(p);
        }
    }
}
