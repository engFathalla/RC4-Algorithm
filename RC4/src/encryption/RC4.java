package encryption;

/**
 *
 * @author mBadr
 */
import java.util.ArrayList;
import java.util.Collections;

public class RC4 {
    public static String Encrypt(String plainText,String key){
        String cipher="";
        ArrayList<Character> keys = generateKeys(key);
        int i=0;
        for(char c:plainText.toCharArray()){
            cipher+=(char)(keys.get(i%keys.size())^c);
        }
        return cipher;
    }
    private static ArrayList<Character> generateKeys(String key){
        ArrayList<Character> s=new ArrayList<>(256);
        ArrayList<Character> keys=new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            s.add(i,(char)i) ;
            keys.add(i,key.charAt(i%key.length()));
        }
        int    j = 0; 
        for (int i = 0;i<256;++i){ 
            j = (j + s.get(i) + keys.get(i))% 256; 
            Collections.swap(s,i,j); 
        } 
    
        int i=0,index;
        j = 0;
        for (int k = 0; k < 256; k++) {
            i = (i + 1)% 256; 
            j = (j + s.get(i))% 256; 
            Collections.swap(s,i,j); 
            index = (s.get(i) + s.get(j))% 256; 
            keys.set(i,s.get(index));
        } 
        return keys;
    }
}
