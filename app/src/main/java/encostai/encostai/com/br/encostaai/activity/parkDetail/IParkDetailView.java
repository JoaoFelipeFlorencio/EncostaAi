package encostai.encostai.com.br.encostaai.activity.parkDetail;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.models.Rating;

public interface IParkDetailView {

    void onStreetRecived(String name, String description, double ratting, boolean favorite, double probability);

    void onPrivateRecived(String name, String description, double v, boolean favorite, int i);

    void showRatings(ArrayList<Rating> ratingList);
}
