package encostai.encostai.com.br.encostaai.activity.main;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.activity.main.Question.IListener;
import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;

public interface IMainInteractor {

    void getPrivateParks(MainListener listner);

    void getStreetParks(MainListener listner);

    void getLocation(GoogleMap mMap, MainListener listener);

    void getStreetParksOnce( IListener iListener);

    void sendAnswerToDB(Boolean answer, String id);

    interface MainListener {

        void onError();

        void onLocationChange(Location location);

        void onPrivateParkRecived(ArrayList<PrivateParking> privateParkingList);

        void onStreetParkListRecived(ArrayList<StreetParking> streetParkList);
    }
    void signOut();

    GoogleMap onMapReady(MainActivity context, GoogleMap mMap, MainListener listner);
}
