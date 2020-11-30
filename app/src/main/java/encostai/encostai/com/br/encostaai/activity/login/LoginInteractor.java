package encostai.encostai.com.br.encostaai.activity.login;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.Base64Custom;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class LoginInteractor implements ILoginInteractor {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListenerUser;
    private String userIdentifier;
    private User user;

    LoginInteractor() {
        this.firebaseAuth = FirebaseConfig.getFirebaseAuth();
        this.user = new User();
    }


    @Override
    public void userLogin(String email, String password, final LoginListener listener, final LoginActivity loginActivity) {


        if (!inputIsEmpty(email, password, listener) && checkEmail(email, listener)) {

            user.setEmail(email);
            user.setPassword(password);


            firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        userIdentifier = Base64Custom.encodeBase64(user.getEmail());
                        databaseReference = FirebaseConfig.getDatabaseReference().child("users").child(userIdentifier);

                        valueEventListenerUser = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User userRecover = dataSnapshot.getValue(User.class);

                                Preferences preferences = new Preferences(loginActivity);
                                preferences.saveData(userIdentifier, userRecover.getName(), userRecover.getEmail(), userRecover.getFavorites(), userRecover.getExposure());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        databaseReference.addListenerForSingleValueEvent(valueEventListenerUser);
                        listener.onSucess();
                    } else {

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            listener.onInputError(2);
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            listener.onInputError(2);
                        } catch (Exception e) {
                            listener.onInputError(3);
                        }


                    }


                }
            });


        }

    }


    private boolean inputIsEmpty(String email, String password, LoginListener listener) {

        if (email.isEmpty() || password.isEmpty()) {
            listener.onInputError(0);
            return true;
        } else {
            return false;
        }


    }


    private boolean checkEmail(String email, LoginListener listener) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return true;
        } else {
            listener.onInputError(1);
            return false;
        }

    }


    public void isUserLogged(LoginListener listener) {
        if (firebaseAuth.getCurrentUser() != null) {
            listener.onSucess();
        }
    }
}
