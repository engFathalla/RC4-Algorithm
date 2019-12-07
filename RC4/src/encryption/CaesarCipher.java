package encryption;
/**
 * 
 * @author mBadr
 */
public class CaesarCipher {
    public static void main(String[] args) {
        layout x = new layout();
        x.setVisible(true);
        
    }
    
    public static String Encrypt(String plaintext, int key) {
        String e = "";
        key %= 26;
        char c;
        for (int i = 0; i < plaintext.length(); i++) {
            c = plaintext.charAt(i);
            if (Character.isAlphabetic(c)) {
                c = Character.toUpperCase(c);
                c += key;
                if (c > 'Z') {
                    c -= 26;
                }
                e += c;
            } 
        }
        return e;
    }
    public static String Decrypt(String ciphertext, int key) {
        String d = "";
        key %= 26;
        char c;
        for (int i = 0; i < ciphertext.length(); i++) {
            c = ciphertext.charAt(i);
            if (Character.isAlphabetic(c)) {
                c = Character.toUpperCase(c);
                c -= key;
                if (c < 'A') {
                    c += 26;
                }
                d += c;
            }
        }
        return d;
    } 
}
