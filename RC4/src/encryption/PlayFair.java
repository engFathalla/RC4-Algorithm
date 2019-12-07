package encryption;

/**
 *
 * @author mBadr
 */
public class PlayFair {
    public static String Encrypt(String plainText,String key){
        String formatedPlainText=formatPlainText(plainText);
        String cipher="";
        String matrix[];
        matrix=getMatrix(key);
        for (int i = 0; i < formatedPlainText.length(); i+=2) {
            cipher+=encryptPlayFair(""+formatedPlainText.charAt(i)+formatedPlainText.charAt(i+1),matrix);
        }
        return cipher;
    } 
    public static String Decrypt(String cipher,String key){
        String decrypted="";
        String matrix[];
        matrix=getMatrix(key);
        for (int i = 0; i < cipher.length(); i+=2) {
            decrypted+=decryptPlayFair(""+cipher.charAt(i)+cipher.charAt(i+1),matrix);
        }
        return decrypted;
    } 
   
    private static String encryptPlayFair(String plainText,String []matrix){
        String result="";
        //
        int i=0,i1=0,j=0,j1=0;
        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {
                if(matrix[k].charAt(l)==plainText.charAt(0)){
                    i=k;
                    j=l;
                }   
                if(matrix[k].charAt(l)==plainText.charAt(1)){
                    i1=k;
                    j1=l;
                }   
            }
        }
        if(i==i1) {
            j++;j1++;
            j%=5;j1%=5;
        }else if(j==j1) {
            i++;i1++;
            i%=5;
            i1%=5;
        }else {
            int temp = j;
            j =  j1;
            j1 = temp;
        }
        result+=matrix[i].charAt(j);
        result+=matrix[i1].charAt(j1);
        return result;
    } 
    private static String decryptPlayFair(String s,String[] matrix){
        String result="";
        int i=0,i1=0,j=0,j1=0;
        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {
                if(matrix[k].charAt(l)==s.charAt(0)){
                    i=k;
                    j=l;
                }   
                if(matrix[k].charAt(l)==s.charAt(1)){
                    i1=k;
                    j1=l;
                }   
            }
        }
        if(i==i1) {
            j--;j1--;
            if(j<0)j=4;
            if(j1<0)j1=4;
        }else if(j==j1) {
            i--;i1--;
            if(i<0)i=4;
            if(i1<0)i1=4;
        }else {
            int temp = j;
            j =  j1;
            j1 = temp;
        }
        result+=matrix[i].charAt(j);
        result+=matrix[i1].charAt(j1);
        return result;
    }
    
    
    private static String formatPlainText(String plainText){
        StringBuilder editablePlainText = new StringBuilder("");
        int tmpi=0;
        for (int i = 0; i < plainText.length(); i++,tmpi++) {
            if(Character.isAlphabetic(plainText.charAt(i))){
                editablePlainText.append(Character.toUpperCase(plainText.charAt(i)));
            if(editablePlainText.charAt(tmpi)=='I'){
                editablePlainText.setCharAt(tmpi,'J' );
            }
            }else {
                tmpi--;
            }
        }
        for(int i=0;i<editablePlainText.length()-1;i+=2){
            if(editablePlainText.charAt(i)==editablePlainText.charAt(i+1)){
                if(editablePlainText.charAt(i)!='X'){
                    editablePlainText.insert(i+1, 'X');
                }else{
                    editablePlainText.insert(i+1, 'Y');
                }
            }
        }
        if(editablePlainText.length()%2==1){
            if(editablePlainText.charAt(editablePlainText.length()-1)=='X'){
                editablePlainText.append('Y');
            }else{
                editablePlainText.append('X');
            }
        }
        return editablePlainText.toString();
    }
    private static String[] getMatrix(String key){
        String matrix[]=new String[5];
        StringBuilder alphapeticLetters = new StringBuilder("ABCDEFGH0JKLMNOPQRSTUVWXYZ");
        StringBuilder editableKey = new StringBuilder();
        int keyLength=0;
        for(Character c:key.toCharArray()){
            c=Character.toUpperCase(c);
            if(c=='I'){
                c='J';
            }
            if( alphapeticLetters.charAt((int)c-(int)'A')!='0'){
                editableKey.append(Character.toUpperCase(c));
                alphapeticLetters.setCharAt(((int)c-(int)'A'), '0');
            }
        }
        int c=0;
        for(int i=0;i<5;++i){
            matrix[i]="";
            for (int j=0; j < 5; j++) {
                while(keyLength==editableKey.length()&&alphapeticLetters.charAt(c)=='0'){
                    ++c;
                }
                if(keyLength==editableKey.length()){
                    matrix[i]+=alphapeticLetters.charAt(c);
                    c++;
                }
                if(keyLength<editableKey.length()){
                    matrix[i]+=editableKey.charAt(keyLength);
                    keyLength++;
                }
            }
        }
        return matrix;
    }
}
