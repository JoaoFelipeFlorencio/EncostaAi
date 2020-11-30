package encostai.encostai.com.br.encostaai.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


import java.util.Date;

import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;

public class Answer {
    private String userId;
    private String streetParkingId;
    private boolean empty;
    private String timestamp;


    public Answer(){

    }

    public void save(){
        DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference();
        timestamp = String.valueOf(new Date().getTime());
        databaseReference.child("answer").child(getStreetParkingId()).push().setValue(this);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public String getStreetParkingId() {
        return streetParkingId;
    }

    public void setStreetParkingId(String streetParkingId) {
        this.streetParkingId = streetParkingId;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
