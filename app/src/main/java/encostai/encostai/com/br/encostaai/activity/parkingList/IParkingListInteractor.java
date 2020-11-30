package encostai.encostai.com.br.encostaai.activity.parkingList;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;

public interface IParkingListInteractor {

    void getPrivateParkingList(final ParkingListListener listener);

    void getStreetParkingList(final ParkingListListener listener);

    interface ParkingListListener{
        void onPrivateParkingListRecived(ArrayList<PrivateParking> privateParkingList);

        void onStreetParkingRecived(ArrayList<StreetParking> streetParkingList);
    }
}
