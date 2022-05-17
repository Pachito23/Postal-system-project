import com.sun.jdi.Value;

public class Cipher {

    public void cipher() {
        Integer a=500;
        Integer b=200;
        System.out.println(hashCode()+" " +b.hashCode());
    }

    public static void main(String[] args) {
        Cipher c = new Cipher();
        c.cipher();
    }
}
