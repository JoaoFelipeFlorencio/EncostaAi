package encostai.encostai.com.br.encostaai.activity.main;

import com.google.android.gms.maps.GoogleMap;

public interface IMainPresenter {
    void signOut();

    GoogleMap onMapReady(MainActivity context, GoogleMap googleMap);

    void continueAddingMarkers();

    void drawPrivatePark();

    void drawStreetPark();

    void getLocation();

    void sendAnswerToInte(Boolean answer, String id);
}
