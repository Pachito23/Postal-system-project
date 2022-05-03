import org.json.JSONObject;
import java.io.FileWriter;
import java.util.ArrayList;

public class Database {
    public static ArrayList<String> empty_list = new ArrayList<>();
    public static void write_new_entry(int Order_status, int TrackingCode, String Courier, int ETA, int Size, ArrayList<String> Sender_info, ArrayList<String> Recipient_info)
    {
        JSONObject parcel = new JSONObject();
        parcel.put("Order_Status", Order_status);
        parcel.put("AWB", TrackingCode);
        parcel.put("Courier", Courier);
        parcel.put("ETA", ETA);
        parcel.put("Size", Size);
        parcel.put("Sender_info", Sender_info);
        parcel.put("Recipient_info", Recipient_info);

        try (FileWriter file = new FileWriter("database.json", true)) {
            file.write(parcel.toString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        write_new_entry(0,0,"Not Assigned yet",0,0,empty_list,empty_list);
    }
}
