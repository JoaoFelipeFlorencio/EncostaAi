package encostai.encostai.com.br.encostaai.activity.rate;

import android.content.Context;

import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.utils.KeyWords;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public class RateInteractor implements IRateInteractor {
    private Context context;
    private Rating rating;
    private Preferences preferences;

    public RateInteractor(RateActivity rateActivity) {
        this.context = rateActivity;
        preferences = new Preferences(this.context);
    }

    @Override
    public void submitRating(String localType, Rating rating, final RateListener listener) {
        if(preferences.getExposure()){
            rating.setUserName(preferences.getName());
        }else{
            rating.setUserName("Anonimo");
        }
        if(localType.equals(KeyWords.PRIVATEPARKING)){
            rating.savePrivateRating(listener);
        }else if(localType.equals(KeyWords.STREETPARKING)){
            rating.saveStreetRating(listener);
        }
    }

}
