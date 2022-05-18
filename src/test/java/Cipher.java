import com.sun.jdi.Value;

public class Cipher {

    public void cipher() {
        String a="abc";
        String b="200";
        String c="abc";
        System.out.println(a.hashCode()+" " +b.hashCode()+" "+c.hashCode());
    }

    public static void main(String[] args) {
        Cipher c = new Cipher();
        c.cipher();
    }
}
