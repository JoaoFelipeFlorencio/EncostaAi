package encostai.encostai.com.br.encostaai.activity.parkDetail;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.models.StreetParking;

public class ParkDetailPresenter implements IParkDetailPresenter, IParkDetailInteractor.ParkDetailListener {

    private IParkDetailView parkDetailView;
    private IParkDetailInteractor parkDetailInteractor;

    public ParkDetailPresenter(ParkDetailActivity parkDetailActivity, ParkDetailInteractor parkDetailInteractor) {
        this.parkDetailView = parkDetailActivity;
        this.parkDetailInteractor = parkDetailInteractor;
    }

    @Override
    public void addFavorite(String parkId){
        parkDetailInteractor.addFavorite(parkId);
    }

    @Override
    public void rmFavorite(String parkId) {
        parkDetailInteractor.rmFavorite(parkId);
    }
    @Override
    public void getStreetParkDetail(String parkId) {
        parkDetailInteractor.getStreetPark(this,parkId);
        parkDetailInteractor.getStreetRatings(this,parkId);
    }

    @Override
    public void onStreetParkRecived(StreetParking streetParking, boolean favorite) {
        parkDetailView.onStreetRecived(streetParking.getName(),streetParking.getDescription(),Double.parseDouble(streetParking.getRating()),favorite,Double.parseDouble(streetParking.getProbability()));
    }

    @Override
    public void getPrivateParkDetail(String parkId){
        parkDetailInteractor.getPrivatePark(this,parkId);
        parkDetailInteractor.getPrivateRatings(this,parkId);
    }



    @Override
    public void onPrivateParkRecifed(PrivateParking privateParking, boolean favorite) {
        parkDetailView.onPrivateRecived(privateParking.getName(),privateParking.getDescription(),Double.parseDouble(privateParking.getRating()),favorite,Integer.parseInt(privateParking.getEnptySpots()));
    }

    @Override
    public void onRatingListRecived(ArrayList<Rating> ratingList) {
        parkDetailView.showRatings(ratingList);
    }
}
