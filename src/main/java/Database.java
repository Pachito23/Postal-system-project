import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    public static ArrayList<String> empty_list = new ArrayList<>();
    public static ArrayList<Parcel> database = new ArrayList<>();
    public static Parcel search_AWB(int AWB)
    {
        for (Parcel item:database)
        {
            if(item.AWB==AWB)
                return item;
        }
        return null;
    }
    public static void print_database()
    {
        for (Parcel parcel:database)
        {
            System.out.println(parcel);
        }
    }
    public static void write_in_database(Parcel parcel)
    {
        database.add(parcel);
    }
    public static void write_in_file()
    {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("database.json"));

            JsonArray total = new JsonArray();

            for(Parcel item:database)
            {
                JsonObject parcel = new JsonObject();
                parcel.put("Order_Status", item.Order_Status);
                parcel.put("AWB", item.AWB);
                parcel.put("Courier", item.Courier);
                parcel.put("ETA", item.ETA);
                parcel.put("Size", item.Size);
                parcel.put("Sender_info", item.Sender_info);
                parcel.put("Recipient_info", item.Recipient_info);
                total.addAll(Arrays.asList(parcel));
            }

            Jsoner.serialize(total, writer);

            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void read_all() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("database.json"));

            JsonArray parser = (JsonArray) Jsoner.deserialize(reader);

            database.clear();

            parser.forEach(entry -> {
                JsonObject item = (JsonObject) entry;
                BigDecimal Order_Status = (BigDecimal) item.get("Order_Status");
                BigDecimal AWB = (BigDecimal) item.get("AWB");
                String Courier = (String) item.get("Courier");
                BigDecimal ETA = (BigDecimal) item.get("ETA");
                BigDecimal Size = (BigDecimal) item.get("Size");
                ArrayList<String> Sender_info = (ArrayList<String>) item.get("Sender_info");
                ArrayList<String> Recipient_info = (ArrayList<String>) item.get("Recipient_info");
                Parcel new_parcel = new Parcel(Order_Status.intValue(),AWB.intValue(),Courier,ETA.intValue(),Size.intValue(),Sender_info,Recipient_info);
                database.add(new_parcel);
            });

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parcel p1= new Parcel(0,0,"No one",0,0,empty_list,empty_list);
        Parcel p2= new Parcel(0,1,"No one",0,0,empty_list,empty_list);
        Database.write_in_database(p1);
        Database.write_in_database(p2);
        Database.write_in_file();
        read_all();
        print_database();
    }
}
