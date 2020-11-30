package encostai.encostai.com.br.encostaai.activity.parkDetail;

public interface IParkDetailPresenter  {

    void addFavorite(String parkId);

    void getStreetParkDetail(String parkId);

    void getPrivateParkDetail(String parkId);

    void rmFavorite(String parkId);
}
