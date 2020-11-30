package encostai.encostai.com.br.encostaai.activity.parkDetail;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class ParkDetailInteractor implements IParkDetailInteractor {

    private Context context;
    private DatabaseReference databaseReference;
    private ValueEventListener streetParkValueListener;
    private ValueEventListener ratingValueListener;
    private ArrayList<Rating> ratingList;
    private ValueEventListener privateParkValueListener;
    private User user;
    private Preferences preferences;

    public ParkDetailInteractor(ParkDetailActivity parkDetailActivity) {
        this.context = parkDetailActivity;
        this.databaseReference = FirebaseConfig.getDatabaseReference();
        preferences = new Preferences(context);
        user = new User();
        user.setName(preferences.getName());
        user.setEmail(preferences.getEmail());
        user.setFavorites(preferences.getFavorites());
        user.setId(preferences.getIdentifier());
        user.setExposure(preferences.getExposure());
        ratingList = new ArrayList<Rating>();
    }

    @Override
    public void getStreetPark(final ParkDetailListener listener, final String parkId) {
        streetParkValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    if(parkId.equals(data.getKey())){
                        StreetParking streetParking = data.getValue(StreetParking.class);
                        streetParking.setId(data.getKey());
                        for (String id:user.getFavorites()) {
                            if(id.equals(data.getKey())){
                                listener.onStreetParkRecived(streetParking,true);
                                return;
                            }
                        }
                        listener.onStreetParkRecived(streetParking,false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("streetParking").addListenerForSingleValueEvent(streetParkValueListener);
    }

    @Override
    public void getPrivatePark(final ParkDetailListener listener, final String parkId) {
        privateParkValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    if(parkId.equals(data.getKey())){
                        PrivateParking privateParking = data.getValue(PrivateParking.class);
                        privateParking.setId(data.getKey());
                        for (String id:user.getFavorites()) {
                            if(id.equals(data.getKey())){
                                listener.onPrivateParkRecifed(privateParking,true);
                                return;
                            }
                        }
                        listener.onPrivateParkRecifed(privateParking,false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("privateParking").addListenerForSingleValueEvent(privateParkValueListener);
    }

    @Override
    public void getPrivateRatings(final ParkDetailListener listener, String parkId) {
        ratingValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Rating rating = data.getValue(Rating.class);
                    rating.setId(data.getKey());
                    ratingList.add(rating);
                }
                listener.onRatingListRecived(ratingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("privateRating").child(parkId).addListenerForSingleValueEvent(ratingValueListener);
    }

    @Override
    public void getStreetRatings(final ParkDetailListener listener, String parkId) {
        ratingValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Rating rating = data.getValue(Rating.class);
                    rating.setId(data.getKey());
                    ratingList.add(rating);
                }
                listener.onRatingListRecived(ratingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("streetRating").child(parkId).addListenerForSingleValueEvent(ratingValueListener);
    }

    @Override
    public void addFavorite(String parkId){
        ArrayList<String> favorites = user.getFavorites();
        favorites.add(parkId);
        //save preference
        preferences.saveData(user.getId(),user.getName(),user.getEmail(),favorites,user.getExposure());
        //save database
        user.setFavorites(favorites);
        user.save();
    }

    @Override
    public void rmFavorite(String parkId) {
        ArrayList<String> favorites = user.getFavorites();
        favorites.remove(parkId);
        //save preference
        preferences.saveData(user.getId(),user.getName(),user.getEmail(),favorites,user.getExposure());
        //save database
        user.setFavorites(favorites);
        user.save();
    }
}
