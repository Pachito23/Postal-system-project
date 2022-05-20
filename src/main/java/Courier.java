import java.util.ArrayList;

public class Courier extends Profile{
    String vehicle_type;
    int max_capacity;
    int current_capacity;

    public Courier(String username, String password,int Profile_type, boolean is_being_read,ArrayList<String> personal_info)
    {
        super(username, password, Profile_type, is_being_read);
        this.vehicle_type=personal_info.get(0);
        this.max_capacity= Integer.valueOf(personal_info.get(1));
        this.current_capacity=Integer.valueOf(personal_info.get(2));
    }

    public Courier()
    {

    }
}
