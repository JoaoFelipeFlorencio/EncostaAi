package encostai.encostai.com.br.encostaai.activity.favorites;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.SimpleParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.KeyWords;

public class FavoritesPresenter implements IFavoritesPresenter, IFavoritesInteractor.FavoritesListener {

    private IFavoritesView favoritesView;
    private IFavoritesInteractor favoritesInteractor;

    private ArrayList<SimpleParking> favoritesStreetList;
    private boolean streetReady = false;
    private ArrayList<SimpleParking> favoritesPrivateList;
    private boolean privateReady = false;
    private ArrayList<SimpleParking> favoritesSimpleList;

    public FavoritesPresenter(FavoritesActivity favoritesActivity, FavoritesInteractor favoritesInteractor) {
        this.favoritesView = favoritesActivity;
        this.favoritesInteractor = favoritesInteractor;
        favoritesStreetList = new ArrayList<SimpleParking>();
        favoritesPrivateList = new ArrayList<SimpleParking>();
        favoritesSimpleList = new ArrayList<SimpleParking>();
    }

    @Override
    public void getFavorites() {
        streetReady = false;
        privateReady = false;
        favoritesInteractor.getFavoriteStreetList( this);
        favoritesInteractor.getFavoritePrivateList(this);
    }

    @Override
    public void onFavoritesPrivateRecived(ArrayList<PrivateParking> favoritesPrivateList){
        this.favoritesPrivateList.clear();
        if(!favoritesPrivateList.isEmpty()) {
            for (PrivateParking privateParking : favoritesPrivateList) {
                SimpleParking simpleParking = new SimpleParking();
                simpleParking.setId(privateParking.getId());
                simpleParking.setName(privateParking.getName());
                simpleParking.setRating(privateParking.getRating());
                simpleParking.setType(KeyWords.PRIVATEPARKING);
                simpleParking.setLatitude(privateParking.getLatitude());
                simpleParking.setLongitude(privateParking.getLongitude());
                this.favoritesPrivateList.add(simpleParking);
            }
        }
        privateReady = true;
        joinFavoritesList();
    }


    @Override
    public void onFavoriteStreetRecived(ArrayList<StreetParking> favoritesStreetList) {
        this.favoritesStreetList.clear();
        if(!favoritesStreetList.isEmpty()){
            for (StreetParking streetParking:favoritesStreetList) {
                SimpleParking simpleParking = new SimpleParking();
                simpleParking.setId(streetParking.getId());
                simpleParking.setName(streetParking.getName());
                simpleParking.setType(KeyWords.STREETPARKING);
                this.favoritesStreetList.add(simpleParking);
            }
        }

        streetReady = true;
        joinFavoritesList();

    }
    @Override
    public synchronized void joinFavoritesList(){
        this.favoritesSimpleList.clear();
        if(streetReady && privateReady){
            favoritesSimpleList.addAll(favoritesStreetList);
            favoritesSimpleList.addAll(favoritesPrivateList);

            favoritesView.showFavoriteSimpleList(favoritesSimpleList);
        }
    }

    @Override
    public void rmFavorite(String parkId) {
        favoritesInteractor.rmFavorite(parkId);
    }
}
