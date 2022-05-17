import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Profile {
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Profile(String username, String password, boolean is_being_read) {

        this.username=username;
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

    public static String Encrypt(String text)
    {
        System.out.println(text);
        return text;
    }
}
