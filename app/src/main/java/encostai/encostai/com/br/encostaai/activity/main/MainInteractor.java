package encostai.encostai.com.br.encostaai.activity.main;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.activity.main.Question.IListener;
import encostai.encostai.com.br.encostaai.models.Answer;
import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class MainInteractor implements IMainInteractor {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener privateParkListener;
    private ValueEventListener streetParkListener;
    private Preferences preferences;

    private ArrayList<PrivateParking> privateParkingList;
    private ArrayList<StreetParking> streetParkingList;

    MainInteractor(Activity MainActivity) {
        this.firebaseAuth = FirebaseConfig.getFirebaseAuth();
        this.databaseReference = FirebaseConfig.getDatabaseReference();
        privateParkingList = new ArrayList<PrivateParking>();
        streetParkingList = new ArrayList<StreetParking>();
        preferences = new Preferences(MainActivity);
    }

    @Override
    public void signOut() {
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseAuth.signOut();
        preferences.clearData();
    }

    @Override
    public GoogleMap onMapReady(MainActivity context, final GoogleMap mMap, final MainListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!mMap.isMyLocationEnabled()) {
                mMap.setMyLocationEnabled(true);

                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }


        }

        return mMap;
    }

    public void getPrivateParks(final MainListener listener) {
        privateParkListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                privateParkingList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    PrivateParking privateParking = data.getValue(PrivateParking.class);
                    privateParking.setId(data.getKey());
                    privateParkingList.add(privateParking);

                }
                if (!privateParkingList.isEmpty()) {
                    listener.onPrivateParkRecived(privateParkingList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("privateParking").addValueEventListener(privateParkListener);

    }

    @Override
    public void getStreetParks(final MainListener listener) {
        streetParkListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                streetParkingList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    StreetParking streetParking = data.getValue(StreetParking.class);
                    streetParking.setId(data.getKey());
                    streetParkingList.add(streetParking);

                }
                if (!streetParkingList.isEmpty()) {
                    listener.onStreetParkListRecived(streetParkingList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("streetParking").addValueEventListener(streetParkListener);
    }


    @Override
    public void getLocation(GoogleMap mMap, final MainListener listener) {
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                listener.onLocationChange(location);

            }
        });
    }

    @Override
    public void getStreetParksOnce(final IListener iListener) {
        databaseReference.child("streetParking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                streetParkingList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    StreetParking streetParking = data.getValue(StreetParking.class);
                    streetParking.setId(data.getKey().toString());
                    streetParkingList.add(streetParking);

                }
                if (!streetParkingList.isEmpty()) {
                    iListener.onSuccess(streetParkingList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void sendAnswerToDB(Boolean isEmpty, String id) {
        Answer awnser = new Answer();
        awnser.setStreetParkingId(id);
        awnser.setUserId(preferences.getIdentifier());
        awnser.setEmpty(isEmpty);
        awnser.save();
    }

}
