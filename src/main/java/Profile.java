import java.util.ArrayList;
public class Profile {
    private String username;
    private String password;
    private int Profile_type;
    protected ArrayList<String> information= new ArrayList<>(3);
    //0->Office Manager   1->Courier   2->Customer

    public String getUsername()
    {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getProfile_type(){ return Profile_type; }

    public ArrayList<String> getinformation(){ return information; }

    public void register(Profile new_profile)
    {
        ProfileDatabase.register(new_profile);
    }

    public Profile()
    {

    }
    public Profile(String username, String password,int Profile_type, boolean is_being_read) {

        this.username=username;
        this.Profile_type=Profile_type;
        if(is_being_read)
            this.password=password;
        else
        {
            try {
                this.password = Encrypt(password + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Profile(String username, String password,int Profile_type, boolean is_being_read, ArrayList<String> information) {

        this.username=username;
        this.Profile_type=Profile_type;
        this.information=information;
        if(is_being_read)
            this.password=password;
        else
        {
            try {
                this.password = Encrypt(password + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return  username;

    }

    public static String Encrypt(String text)
    {
        String encoded = text.hashCode()+"";
        return encoded;
    }
}
