import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileDatabase {
    private static ArrayList<Profile> database = new ArrayList<>();

    public static boolean login(String username, String password)
    {
        database.clear();
        read_all();
        String password_encrypted = Profile.Encrypt(password+username);
        System.out.println("To find:"+username+" -> "+password_encrypted);
        for(Profile p:database)
        {
            System.out.println("Now at:"+p.getUsername()+" -> "+p.getPassword());
            if(p.getUsername().equals(username) && p.getPassword().equals(password_encrypted))
            {
                database.clear();
                return true;
            }
        }
        database.clear();
        return false;
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
                total.addAll(Arrays.asList(obj));
            }

            Jsoner.serialize(total, writer);

            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void read_all() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("LoginDatabase.json"));
            File file = new File("LoginDatabase.json");

            if(file.length()!=0) {
                JsonArray parser = (JsonArray) Jsoner.deserialize(reader);

                database.clear();

                parser.forEach(entry -> {
                    JsonObject item = (JsonObject) entry;
                    String username = (String) item.get("username");
                    String password = (String) item.get("password");
                    Profile new_profile = new Profile(username, password,true);
                    database.add(new_profile);
                });

                reader.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void print()
    {
        for(Profile p:database)
        {
            System.out.println(p.getUsername() + "->" + p.getPassword());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Profile p1 = new Profile("Guy1","123",false);
        Profile p2 = new Profile("Guy2","789",false);
        ProfileDatabase.register(p1);
        ProfileDatabase.register(p2);
        System.out.println(login("Guy1","123"));
    }
}
