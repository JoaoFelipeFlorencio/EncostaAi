package encostai.encostai.com.br.encostaai.activity.favorites;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.User;

public interface IFavoritesPresenter {
    void getFavorites();

    void joinFavoritesList();

    void rmFavorite(String parkId);
}
