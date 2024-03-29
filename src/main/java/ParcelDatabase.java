import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ParcelDatabase {
    protected static long AWB_counter=31415926;
    public static ArrayList<Parcel> database = new ArrayList<>();

    public static Parcel search_AWB(long AWB)
    {
        read_all();
        for (Parcel item:database)
        {
            if(item.AWB==AWB) {
                return item;
            }
        }
        database.clear();
        return null;
    }

    public static void delete_from_database_by_AWB(long AWB)
    {
        read_all();
        Parcel p = search_AWB(AWB);
        if(p!=null) {
            database.remove(p);
        }
        write_in_file();
    }

    public static void empty()
    {
        database.clear();
        write_in_file();
    }

    public static void exists_in_database(Parcel toSearch){
        System.out.println("Database has object: "+ database.contains(toSearch));
    }

    public static void delete_from_database_by_instance(Parcel toDelete){
        read_all();
        database.remove(toDelete);
        write_in_file();
    }
    public static void update_database()
    {
        write_in_file();
    }

    public static void print_database()
    {
        if(database.isEmpty())
        {
            System.out.println("Empty database");
            return;
        }
        for (Parcel parcel:database)
        {
            System.out.println(parcel);
        }
    }
    public static void add_to_database(Parcel parcel)
    {
        read_all();
        database.add(parcel);
        write_in_file();
    }

    public static void delete_database(){
        database.clear();
        write_in_file();
    }

    static void write_in_file()
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
        database.clear();
        File file = new File("database.json");

        try {
            Reader reader = Files.newBufferedReader(Paths.get("database.json"));

            if(file.length()!=0) {
                JsonArray parser = (JsonArray) Jsoner.deserialize(reader);

                parser.forEach(entry -> {
                    JsonObject item = (JsonObject) entry;
                    BigDecimal Order_Status = (BigDecimal) item.get("Order_Status");
                    BigDecimal AWB = (BigDecimal) item.get("AWB");
                    String Courier = (String) item.get("Courier");
                    String ETA = (String) item.get("ETA");
                    BigDecimal Size = (BigDecimal) item.get("Size");
                    ArrayList<String> Sender_info = (ArrayList<String>) item.get("Sender_info");
                    ArrayList<String> Recipient_info = (ArrayList<String>) item.get("Recipient_info");
                    Parcel new_parcel = new Parcel(Order_Status.intValue(), AWB.longValue(), Courier, ETA, Size.intValue(), Sender_info, Recipient_info);
                    if(new_parcel.AWB>=AWB_counter)
                        AWB_counter=new_parcel.AWB+1;
                    database.add(new_parcel);
                });

                reader.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
