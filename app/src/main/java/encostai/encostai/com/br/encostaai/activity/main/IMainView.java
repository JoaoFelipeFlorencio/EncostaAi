package encostai.encostai.com.br.encostaai.activity.main;

import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public interface IMainView {
    void onClick(View v);

    void addMarker(String id, LatLng latLng, String tittle);

    void addLine(String id, LatLng latLng1, LatLng latLng2, String tittle);

    void goTo(LatLng latLng);

    void newLocation(Location location);

    void askQuestion(String id);

    void finishLoading();

    void clearMarkers();
}
