package encostai.encostai.com.br.encostaai.activity.parkingList;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.SimpleParking;

public interface IParkingListView {
    void showSimpleParkingList(ArrayList<SimpleParking> simpleStreetParkingList);

    void erroGps();
}
