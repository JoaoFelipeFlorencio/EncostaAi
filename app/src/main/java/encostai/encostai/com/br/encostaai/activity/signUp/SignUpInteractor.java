package encostai.encostai.com.br.encostaai.activity.signUp;

import android.support.annotation.NonNull;
import android.util.ArraySet;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.Base64Custom;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class SignUpInteractor implements ISignUpInteractor {

    private FirebaseAuth firebaseAuth;
    private User user;

    SignUpInteractor() {

        this.firebaseAuth = FirebaseConfig.getFirebaseAuth();
        this.user = new User();
    }


    public void addUser(String name, String email, String password, String confirmPassword, final SignUpListener listener, final SignUpActivity signUpActivity, boolean exposure) {

        if (!inputIsEmpty(name, email, password, listener) && checkPassword(password, confirmPassword, listener) && checkEmail(email, listener)) {

            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            user.setExposure(exposure);

            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(signUpActivity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String userIdentifier = Base64Custom.encodeBase64(user.getEmail());
                        user.setId(userIdentifier);
                        user.save();

                        Preferences preferences = new Preferences(signUpActivity);
                        preferences.saveData(userIdentifier, user.getName(), user.getEmail(), new ArrayList<String>(),user.getExposure());
                        listener.onSucess();
                    } else {

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            listener.onInputError(3);
                        } catch (FirebaseAuthUserCollisionException e) {
                            listener.onInputError(1);
                        } catch (Exception e) {
                            listener.onInputError(5);
                        }


                    }


                }
            });

        }
    }


    private boolean inputIsEmpty(String name, String email, String password, SignUpListener listener) {

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            listener.onInputError(0);
            return true;
        } else {
            return false;
        }
    }


    private boolean checkPassword(String password, String confirmPassword, SignUpListener listener) {

        if (password.equals(confirmPassword)) {
            return true;
        } else {
            listener.onInputError(4);
            return false;
        }
    }

    private boolean checkEmail(String email, SignUpListener listener) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            listener.onInputError(2);
            return false;
        }
    }


}
