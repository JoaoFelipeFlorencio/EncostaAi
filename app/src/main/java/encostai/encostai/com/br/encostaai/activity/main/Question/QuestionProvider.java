package encostai.encostai.com.br.encostaai.activity.main.Question;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.activity.main.IMainInteractor;
import encostai.encostai.com.br.encostaai.activity.main.IMainPresenter;
import encostai.encostai.com.br.encostaai.activity.main.MainActivity;
import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;

public class QuestionProvider implements GPSCallback, Runnable {


    private GPSManager gpsManager = null;
    private double speed = 0.0;
    private double currentSpeed, kmphSpeed;
    private MainActivity activity;
    private IMainInteractor interactor;

    @Override
    public void run() {

        gpsManager = new GPSManager(activity);
        gpsManager.startListening(activity.getApplicationContext());
        gpsManager.setGPSCallback(this);
    }

    public QuestionProvider(MainActivity activity, IMainInteractor interactor) {
        this.activity = activity;
        this.interactor = interactor;
    }

    @Override
    public void onGPSUpdate(final Location location) {
        speed = location.getSpeed();
        currentSpeed = round(speed, 3, BigDecimal.ROUND_HALF_UP);
        kmphSpeed = round((currentSpeed * 3.6), 3, BigDecimal.ROUND_HALF_UP);
        if (kmphSpeed < 5 && activity.getAskQuestion()) {
            interactor.getStreetParksOnce(new IListener() {
                @Override
                public void onSuccess(ArrayList<StreetParking> streetParkingList) {
                    double distance = 0.0;
                    double closestDistance = Double.MAX_VALUE;
                    String closestId = new String();
                    for (StreetParking streetParking : streetParkingList) {
                        Location streetLocation = new Location("");
                        streetLocation.setLatitude((Double.parseDouble(streetParking.getLatitude1()) + Double.parseDouble(streetParking.getLatitude2())) / 2);
                        streetLocation.setLongitude((Double.parseDouble(streetParking.getLongitude1()) + Double.parseDouble(streetParking.getLongitude2())) / 2);

                        distance = location.distanceTo(streetLocation);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestId = streetParking.getId();
                        }

                    }
                    if (closestDistance <= 50.00) {
                        activity.askQuestion(closestId);
                    }
                }
            });
        }
    }


    private static double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }


    public void destroyManager() {
        gpsManager.stopListening();
        gpsManager.setGPSCallback(null);
        gpsManager = null;
    }


}
