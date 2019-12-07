package encryption;

/**
 *
 * @author mBadr
 */
public class Feistel {
    private static final byte[] bitsRotationTable = new byte[]{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    public static String Encrypt(String plainText,String key,char op){
        String fCipher="";
        plainText=formatPlainText(plainText);
        for (int i = 0; i < plainText.length(); i+=8) {
            String tmpCipher=Encrypt64Bit(plainText.substring(i,i+8), key,op);
            fCipher+=tmpCipher;
        }
        return fCipher;
    }
    private static String Encrypt64Bit(String plainText,String key,char op){
        String cipher;
        
        plainText=charactersToBinary(plainText);
        String []keys=generateKeys(key);
        String right=plainText.substring(plainText.length()/2),prevRight;
        String left=plainText.substring(0,plainText.length()/2);
        for (int i = 0; i < 16; i++) {
            prevRight=right;
            right=rightOperation(right, keys[i],op);
            long rightNum=Long.parseLong(right, 2);
            long leftNum=Long.parseLong(left, 2);
            right=Long.toBinaryString(rightNum^leftNum);
            while(right.length()<32){
                right="0"+right;
            }
            left=prevRight;
        }
        cipher=right+left;
        cipher=binarytoString(cipher);
        return cipher;
    }
    public static String Decrypt(String cipher,String key,char op){
        String fDecryption="";
        cipher=formatPlainText(cipher);
        for (int i = 0; i < cipher.length(); i+=8) {
            String tmpDecryption=Decrypt64Bit(cipher.substring(i,i+8), key,op);
            fDecryption+=tmpDecryption;
        }
        return fDecryption;
    }
    private static String Decrypt64Bit(String cipher,String key,char op){
        String decrypted;
        
        cipher=charactersToBinary(cipher);
        String []keys=generateKeys(key);
        String right=cipher.substring(cipher.length()/2),prevRight;
        String left=cipher.substring(0,cipher.length()/2);
        for (int i = 0; i < 16; i++) {
            prevRight=right;
            right=rightOperation(right, keys[15-i],op);
            long rightNum=Long.parseLong(right, 2);
            long leftNum=Long.parseLong(left, 2);
            right=Long.toBinaryString(rightNum^leftNum);
            while(right.length()<32){
                right="0"+right;
            }
            left=prevRight;
        }
        decrypted=right+left;
        decrypted=binarytoString(decrypted);
        return decrypted;
    }
    private static String rightOperation(String right,String key,char op){
        long rightNum=Long.parseLong(right, 2);
        long keyNum=Long.parseLong(key, 2);
        switch (op){
            case 'X' :{
                right=Long.toBinaryString(rightNum^keyNum);
                break;
            }
            case 'A' :{
                right=Long.toBinaryString(rightNum&keyNum);
                break;
            }
            case 'O' :{
                right=Long.toBinaryString(rightNum|keyNum);
                break;
            }
        }
        while(right.length()<32){
            right="0"+right;
        }
        return right;
    }
    private static String[] generateKeys(String key){
        String keyInBin =charactersToBinary(key);
        String keys[] = new String[16];
        String prev=keyInBin;
        for (int i = 0; i < 16; i++) {
            keyInBin=prev.substring(bitsRotationTable[i]);
            keyInBin+=prev.substring(0,bitsRotationTable[i]);
            prev=keyInBin;
            keys[i]=keyInBin;
        }
        return keys;
    }
    private static String charactersToBinary(String characters){
        String inBinary="";
        String _8bit;
        for (int i = 0; i < characters.length(); i++) {
            _8bit=Integer.toBinaryString(characters.charAt(i));
            while(_8bit.length()<8){
                _8bit="0"+_8bit;
            }
            inBinary+=_8bit;
        }
        return inBinary;
    }
    private static String binarytoString(String _64bit){
        String _8bit;
        String characters="";
        for (int i = 0; i < 64; i+=8) {
            _8bit=""+(char)Integer.parseInt(_64bit.substring(i, i+8),2);
            characters +=_8bit;
        }
        return characters;
    }
    
    
    private static String formatPlainText(String plainText){
        while(plainText.length()%8!=0){
            plainText+="x";
        }
        return plainText;
    }
}
