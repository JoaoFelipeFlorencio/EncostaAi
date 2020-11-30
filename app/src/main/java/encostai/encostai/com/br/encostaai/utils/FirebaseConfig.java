package encostai.encostai.com.br.encostaai.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    //recupera referencia ao database
    public static DatabaseReference getDatabaseReference(){
        if(databaseReference==null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    //recupera instancia do auth
    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth==null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}
