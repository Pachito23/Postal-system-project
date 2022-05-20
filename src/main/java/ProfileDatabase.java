import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileDatabase {
    private static ArrayList<Profile> database = new ArrayList<>();

    public static Profile login(String username, String password)
    {
        database.clear();
        read_all();
        String password_encrypted = Profile.Encrypt(password+username);
        //System.out.println("To find:"+username+" -> "+password_encrypted);
        for(Profile p:database)
        {
            //System.out.println("Now at:"+p.getUsername()+" -> "+p.getPassword());
            if(p.getUsername().equals(username) && p.getPassword().equals(password_encrypted)) {
                database.clear();
                if(p.getProfile_type()==0) {
                    return new Office_Manager(p.getUsername(), p.getPassword(), p.getProfile_type(), true, p.getinformation());
                }
                if (p.getProfile_type() == 1) {
                    return new Courier(p.getUsername(), p.getPassword(), p.getProfile_type(), true, p.getinformation());
                }
                if (p.getProfile_type() == 2) {
                    return new Customer(p.getUsername(), p.getPassword(), p.getProfile_type(), true, p.getinformation());
                }
            }
        }
        database.clear();
        return null;
    }

    public static boolean Add_cargo_courier(String username,int cargo_to_add)
    {
        read_all();
        for(Profile p:database)
        {
            if(p.getUsername().equals(username) && p.getProfile_type()==1)
            {
                int new_size = Integer.valueOf(p.information.get(2))+cargo_to_add;
                if(new_size>Integer.valueOf(p.information.get(1)))
                {
                    System.out.println("Courier already full");
                    return false;
                }
                p.information.remove(2);
                p.information.add(2,Integer.toString(new_size));
            }
        }
        write_in_file();
        database.clear();
        return true;
    }

    public static void register(Profile new_profile)
    {
        database.clear();
        read_all();
        for(Profile p:database)
        {
            if(p.getUsername().equals(new_profile.getUsername()))
            {
                System.out.println("Username already used");
                return;
            }
        }
        database.add(new_profile);
        write_in_file();
        database.clear();
    }

    private static void write_in_file()
    {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("LoginDatabase.json"));

            JsonArray total = new JsonArray();

            for(Profile item:database)
            {
                JsonObject obj = new JsonObject();
                obj.put("username", item.getUsername());
                obj.put("password", item.getPassword());
                obj.put("Profile_type",item.getProfile_type());
                obj.put("information",item.getinformation());
                total.addAll(Arrays.asList(obj));
            }

            Jsoner.serialize(total, writer);

            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void read_all() {
        database.clear();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("LoginDatabase.json"));
            File file = new File("LoginDatabase.json");

            if(file.length()!=0) {
                JsonArray parser = (JsonArray) Jsoner.deserialize(reader);

                parser.forEach(entry -> {
                    JsonObject item = (JsonObject) entry;
                    String username = (String) item.get("username");
                    String password = (String) item.get("password");
                    BigDecimal profile_type = (BigDecimal) item.get("Profile_type");
                    ArrayList<String> information = (ArrayList<String>) item.get("information");
                    Profile new_profile = new Profile(username, password,profile_type.intValue(),true, information);
                    database.add(new_profile);
                });

                reader.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean courier_exists(String username)
    {
        read_all();
        for(Profile p:database)
        {
            if(p.getUsername().equals(username) && p.getProfile_type()==1)
                return true;
        }
        return false;
    }

    public static void print()
    {
        read_all();
        for(Profile p:database)
        {
            System.out.println(p.getUsername() + "->" + p.getPassword());
        }
        System.out.println();
        database.clear();
    }
}