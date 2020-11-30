package encostai.encostai.com.br.encostaai.activity.profile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.Base64Custom;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class ProfileInteractor implements IProfileInteractor {


    private FirebaseAuth firebaseAuth;
    private AuthCredential credential;
    private FirebaseUser firebaseUser;
    private User user;
    private Preferences mPreferences;
    private Context context;

    ProfileInteractor(Context context) {
        this.context = context;
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        user = new User();
        mPreferences = new Preferences(context);
        user.setId(Base64Custom.encodeBase64(mPreferences.getEmail()));
        user.setEmail(mPreferences.getEmail());
        user.setName(mPreferences.getName());
        user.setExposure(mPreferences.getExposure());
        user.setFavorites(mPreferences.getFavorites());
    }

    @Override
    public void changeInfo(final User user, String password, final ProfileListener profileListener) {
        if(!user.getName().isEmpty()){
            this.user.setName(user.getName());
        }
        this.user.setExposure(user.getExposure());
        if(!user.getPassword().isEmpty()){
            credential = EmailAuthProvider.getCredential(this.user.getEmail().toString(),password);
            firebaseUser = firebaseAuth.getCurrentUser();
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseUser.updatePassword(user.getPassword()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            saveUpdate(profileListener);
                        }
                    });

                }
            });
        }else{
            saveUpdate(profileListener);
        }




    }

    private void saveUpdate(ProfileListener profileListener){
        this.user.save();
        mPreferences.saveData(this.user.getId(),this.user.getName(),this.user.getEmail(),this.user.getFavorites(),this.user.getExposure());
        profileListener.onSucess();
    }

}
