import java.util.ArrayList;

public class Parcel {
    public int Order_Status;
    public int AWB;
    public String Courier;
    public String ETA;
    public int Size;
    public ArrayList<String> Sender_info;
    public ArrayList<String> Recipient_info;
    public Parcel(int Order_status, int AWB, String Courier, String ETA, int Size, ArrayList<String> Sender_info, ArrayList<String> Recipient_info)
    {
        this.Order_Status=Order_status;
        this.AWB=AWB;
        this.Courier=Courier;
        this.ETA=ETA;
        this.Size=Size;
        this.Sender_info=Sender_info;
        this.Recipient_info=Recipient_info;
    }

    public String personal_info(ArrayList<String> info)
    {
        if(info==null || info.isEmpty())
            return "Not set";
        String aux="";
        aux =aux+ "Name: "+info.get(0)+'\n';
        aux =aux+ "Phone number: "+info.get(1)+'\n';
        aux =aux+ "Address: "+info.get(2)+'\n';
        return aux;
    }

    public String toString()
    {
        String to_print="";
        to_print = to_print+"Order status: "+Order_Status+" - ";
        to_print = to_print+"AWB: "+AWB+" - ";
        to_print = to_print+"Courier: "+Courier+" - ";
        to_print = to_print+"ETA: "+ETA+" - ";
        to_print = to_print+"Size: "+Size+"\n";
        to_print = to_print+"\tSender info: \n"+ personal_info(Sender_info);
        to_print = to_print+"\tRecipient info: \n"+personal_info(Recipient_info);
        return to_print;
    }
}
