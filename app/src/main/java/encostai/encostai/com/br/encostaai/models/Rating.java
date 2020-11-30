package encostai.encostai.com.br.encostaai.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import encostai.encostai.com.br.encostaai.activity.rate.IRateInteractor;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;

public class Rating {

    private String userName;
    private String localId;
    private String description;
    private int rate;
    private String id;

    public void saveStreetRating(final IRateInteractor.RateListener listener){
        DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference();
        databaseReference.child("streetRating").child(localId).push().setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    listener.onSuccess();
                }else{
                    listener.onError();
                }
            }
        });
    }

    public void savePrivateRating(final IRateInteractor.RateListener listener){
        DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference();
        databaseReference.child("privateRating").child(localId).push().setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    listener.onSuccess();
                }else{
                    listener.onError();
                }
            }
        });
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Exclude
    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

}
