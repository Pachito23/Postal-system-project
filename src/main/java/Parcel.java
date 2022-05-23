import java.util.ArrayList;
import java.util.Objects;

public class Parcel {
    public int Order_Status;
    public long AWB;
    public String Courier;
    public String ETA;
    public int Size;
    public ArrayList<String> Sender_info;
    public ArrayList<String> Recipient_info;

    public int getOrder_Status() {
        return Order_Status;
    }

    public long getAWB() {
        return AWB;
    }

    public String getCourier() {
        return Courier;
    }

    public String getETA() {
        return ETA;
    }

    public int getSize() {
        return Size;
    }

    public ArrayList<String> getSender_info() {
        return Sender_info;
    }

    public ArrayList<String> getRecipient_info() {
        return Recipient_info;
    }

    public Parcel(int Order_status, long AWB, String Courier, String ETA, int Size, ArrayList<String> Sender_info, ArrayList<String> Recipient_info)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcel parcel = (Parcel) o;
        return Order_Status == parcel.Order_Status && AWB == parcel.AWB && Size == parcel.Size && Objects.equals(Courier, parcel.Courier) && Objects.equals(ETA, parcel.ETA) && Objects.equals(Sender_info, parcel.Sender_info) && Objects.equals(Recipient_info, parcel.Recipient_info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Order_Status, AWB, Courier, ETA, Size, Sender_info, Recipient_info);
    }
}
