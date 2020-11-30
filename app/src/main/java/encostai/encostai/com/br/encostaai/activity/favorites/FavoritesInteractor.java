package encostai.encostai.com.br.encostaai.activity.favorites;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class FavoritesInteractor implements IFavoritesInteractor {

    private Context context;
    private DatabaseReference databaseReference;
    private ValueEventListener favoriteStreetValueListener;
    private ValueEventListener favoritePrivateValueListener;
    private ArrayList<StreetParking> favoritesStreetList;
    private ArrayList<PrivateParking> favoritesPrivateList;
    private Preferences preferences;
    private User user;

    public FavoritesInteractor(FavoritesActivity favoritesActivity) {
        this.context = favoritesActivity;
        this.databaseReference = FirebaseConfig.getDatabaseReference();
        this.favoritesStreetList = new ArrayList<StreetParking>();
        this.favoritesPrivateList = new ArrayList<PrivateParking>();
        preferences = new Preferences(context);
        user = new User();
        user.setId(preferences.getIdentifier());
        user.setName(preferences.getName());
        user.setEmail(preferences.getEmail());
        user.setExposure(preferences.getExposure());
        user.setFavorites((ArrayList<String>) preferences.getFavorites());
    }

    @Override
    public void getFavoriteStreetList(final FavoritesListener listener) {
        favoriteStreetValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favoritesStreetList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    StreetParking streetParking = data.getValue(StreetParking.class);
                    streetParking.setId(data.getKey());
                    for (String id : user.getFavorites()) {
                        if (id.equals(data.getKey())) {
                            favoritesStreetList.add(streetParking);
                        }
                    }
                }
                listener.onFavoriteStreetRecived(favoritesStreetList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("streetParking").addValueEventListener(favoriteStreetValueListener);
    }

    @Override
    public void getFavoritePrivateList(final FavoritesListener listener) {
        favoritePrivateValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favoritesPrivateList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    PrivateParking privateParking = data.getValue(PrivateParking.class);
                    privateParking.setId(data.getKey());
                    for (String id : user.getFavorites()) {
                        if (id.equals(data.getKey())) {
                            favoritesPrivateList.add(privateParking);
                        }
                    }
                }
                listener.onFavoritesPrivateRecived(favoritesPrivateList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("privateParking").addValueEventListener(favoritePrivateValueListener);
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
