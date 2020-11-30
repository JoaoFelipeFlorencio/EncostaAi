package encostai.encostai.com.br.encostaai.activity.rate;

import encostai.encostai.com.br.encostaai.models.Rating;

public class RatePresenter implements IRatePresenter, IRateInteractor.RateListener {

    private IRateView rateView;
    private IRateInteractor interactor;
    private Rating rating;

    public RatePresenter(RateActivity rateActivity, RateInteractor rateInteractor) {
        this.rateView = rateActivity;
        this.interactor = rateInteractor;
        rating = new Rating();
    }

    @Override
    public void submitRate(String localType, String localId, int rate, String s) {
        rating.setLocalId(localId);
        rating.setRate(rate);
        rating.setDescription(s);
        interactor.submitRating(localType,rating,this);
    }

    @Override
    public void onSuccess() {
        rateView.submitSucces();
    }

    @Override
    public void onError() {

    }
}
