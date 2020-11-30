package encostai.encostai.com.br.encostaai.utils;

import android.util.Base64;

public class Base64Custom {

    //transforma texto para base64
    public static String encodeBase64(String text){
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    //transforma o texto de base64 para real
    public  static String decodeBase64(String encodedText){
        return new String(Base64.decode(encodedText, Base64.DEFAULT));
    }

}
