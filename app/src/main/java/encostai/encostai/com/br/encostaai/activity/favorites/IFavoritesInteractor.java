package encostai.encostai.com.br.encostaai.activity.favorites;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.models.User;

public interface IFavoritesInteractor {

    void getFavoriteStreetList(final FavoritesListener listener);

    void getFavoritePrivateList(final FavoritesListener listener);

    void rmFavorite(String parkId);

    interface FavoritesListener{

        void onFavoritesPrivateRecived(ArrayList<PrivateParking> privateParkingList);

        void onFavoriteStreetRecived(ArrayList<StreetParking> favoritesStreetList);
    }
}
