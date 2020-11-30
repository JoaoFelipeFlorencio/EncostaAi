package encostai.encostai.com.br.encostaai.activity.parkingList;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;

public class ParkingListInteractor implements IParkingListInteractor {

    private Context context;
    private DatabaseReference databaseReference;
    private ValueEventListener privateParkingListListener;
    private ArrayList<PrivateParking> privateParkingList;
    private ValueEventListener streetParkingListListner;
    private ArrayList<StreetParking> streetParkingList;

    public ParkingListInteractor(ParkingListActivity parkingListActivity) {
        this.context = parkingListActivity;
        this.databaseReference = FirebaseConfig.getDatabaseReference();
        this.privateParkingList = new ArrayList<PrivateParking>();
        this.streetParkingList = new ArrayList<StreetParking>();
    }

    @Override
    public synchronized void getPrivateParkingList(final ParkingListListener listener) {
        privateParkingListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                privateParkingList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    PrivateParking privateParking = data.getValue(PrivateParking.class);
                    privateParking.setId(data.getKey());
                    privateParkingList.add(privateParking);
                }
                if(!privateParkingList.isEmpty()){
                    listener.onPrivateParkingListRecived(privateParkingList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("privateParking").addValueEventListener(privateParkingListListener);
    }

    @Override
    public synchronized void getStreetParkingList(final ParkingListListener listener) {
        streetParkingListListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                streetParkingList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    StreetParking streetParking = data.getValue(StreetParking.class);
                    streetParking.setId(data.getKey());
                    streetParkingList.add(streetParking);
                }
                if(!streetParkingList.isEmpty()){
                    listener.onStreetParkingRecived(streetParkingList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.child("streetParking").addValueEventListener(streetParkingListListner);
    }
}
