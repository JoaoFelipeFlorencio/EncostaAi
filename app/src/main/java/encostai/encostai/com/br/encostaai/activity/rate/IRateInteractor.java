package encostai.encostai.com.br.encostaai.activity.rate;

import encostai.encostai.com.br.encostaai.models.Rating;

public interface IRateInteractor {


    void submitRating(String localType, Rating rating, RateListener listener);

    interface RateListener{

        void onSuccess();

        void onError();
    }
}
