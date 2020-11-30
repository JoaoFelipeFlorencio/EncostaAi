package encostai.encostai.com.br.encostaai.activity.parkDetail;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.models.StreetParking;

public interface IParkDetailInteractor {
    
    void getStreetPark(ParkDetailListener listener, String parkId);

    void getPrivatePark(ParkDetailListener listener, String parkId);

    void getPrivateRatings(ParkDetailListener listener, String parkId);

    void getStreetRatings(ParkDetailListener listener, String parkId);

    void addFavorite(String parkId);

    void rmFavorite(String parkId);

    interface ParkDetailListener{

        void onStreetParkRecived(StreetParking streetParking,boolean favorite);

        void onPrivateParkRecifed(PrivateParking privateParking, boolean favorite);

        void onRatingListRecived(ArrayList<Rating> ratingList);
    }
}
