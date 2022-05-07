import java.util.ArrayList;

public class Parcel {
    public int Order_Status;
    public int AWB;
    public String Courier;
    public int ETA;
    public int Size;
    public ArrayList<String> Sender_info;
    public ArrayList<String> Recipient_info;
    public Parcel(int Order_status, int AWB, String Courier, int ETA, int Size, ArrayList<String> Sender_info, ArrayList<String> Recipient_info)
    {
        this.Order_Status=Order_status;
        this.AWB=AWB;
        this.Courier=Courier;
        this.ETA=ETA;
        this.Size=Size;
        this.Sender_info=Sender_info;
        this.Recipient_info=Recipient_info;
    }
    public String toString()
    {
        String to_print="";
        to_print = to_print+"Order status: "+Order_Status+" - ";
        to_print = to_print+"AWB: "+AWB+" - ";
        to_print = to_print+"Courier: "+Courier+" - ";
        to_print = to_print+"ETA: "+ETA+" - ";
        to_print = to_print+"Size: "+Size+" - ";
        to_print = to_print+"Sender info: "+ Sender_info+" - ";
        to_print = to_print+"Recipient info: "+Recipient_info;
        return to_print;
    }
}
