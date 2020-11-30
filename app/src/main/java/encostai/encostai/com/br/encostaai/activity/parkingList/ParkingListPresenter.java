package encostai.encostai.com.br.encostaai.activity.parkingList;

import android.location.Location;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.models.PrivateParking;
import encostai.encostai.com.br.encostaai.models.SimpleParking;
import encostai.encostai.com.br.encostaai.models.StreetParking;
import encostai.encostai.com.br.encostaai.utils.KeyWords;

public class ParkingListPresenter implements IParkingListPresenter, IParkingListInteractor.ParkingListListener {

    private IParkingListView parkingListView;
    private IParkingListInteractor parkingListInteractor;

    private ArrayList<SimpleParking> simpleParkingList;
    private ArrayList<SimpleParking> privateParkingList;
    private boolean privateReady = false;
    private boolean privateChecked;
    private ArrayList<SimpleParking> streetParkingList;
    private boolean streetReady = false;
    private boolean publicChecked;
    private RadioGroup radioGroup;
    private Location location;

    public ParkingListPresenter(IParkingListView parkingListView, ParkingListInteractor parkingListInteractor, Location location) {
        this.parkingListView = parkingListView;
        this.parkingListInteractor = parkingListInteractor;
        this.privateParkingList = new ArrayList<SimpleParking>();
        this.streetParkingList = new ArrayList<SimpleParking>();
        this.simpleParkingList = new ArrayList<SimpleParking>();
        this.location = location;
    }


    @Override
    public synchronized void getParkingList(boolean publicChecked, boolean privateChecked, RadioGroup sortRadioGrouop) {
        streetReady = false;
        privateReady = false;
        this.publicChecked = publicChecked;
        this.privateChecked = privateChecked;
        this.radioGroup = sortRadioGrouop;


        if (publicChecked) {
            if (streetParkingList.isEmpty()) {
                parkingListInteractor.getStreetParkingList(this);
            } else {
                streetReady = true;
            }
        } else {
            streetParkingList.clear();
        }
        if (privateChecked) {
            if (privateParkingList.isEmpty()) {
                parkingListInteractor.getPrivateParkingList(this);
            } else {
                privateReady = true;
            }
        } else {
            privateParkingList.clear();
        }
        joinParkingList();
    }

    @Override
    public synchronized void onPrivateParkingListRecived(ArrayList<PrivateParking> privateParkingList) {
        this.privateParkingList.clear();
        for (PrivateParking privateParking : privateParkingList) {
            SimpleParking simpleParking = new SimpleParking();
            simpleParking.setId(privateParking.getId());
            simpleParking.setName(privateParking.getName());
            simpleParking.setRating(privateParking.getRating());
            simpleParking.setType(KeyWords.PRIVATEPARKING);
            simpleParking.setLatitude(privateParking.getLatitude());
            simpleParking.setLongitude(privateParking.getLongitude());
            this.privateParkingList.add(simpleParking);
        }
        privateReady = true;
        joinParkingList();

    }

    @Override
    public synchronized void onStreetParkingRecived(ArrayList<StreetParking> streetParkingList) {
        this.streetParkingList.clear();
        for (StreetParking streetParking : streetParkingList) {
            SimpleParking simpleParking = new SimpleParking();
            simpleParking.setId(streetParking.getId());
            simpleParking.setName(streetParking.getName());
            simpleParking.setRating(streetParking.getRating());
            simpleParking.setType(KeyWords.STREETPARKING);
            simpleParking.setLatitude(streetParking.getLatitude1());
            simpleParking.setLongitude(streetParking.getLongitude1());
            this.streetParkingList.add(simpleParking);
        }
        streetReady = true;
        joinParkingList();


    }

    @Override
    public synchronized void joinParkingList() {
        this.simpleParkingList.clear();
        if (streetReady == publicChecked && privateReady == privateChecked) {

            simpleParkingList.addAll(streetParkingList);
            simpleParkingList.addAll(privateParkingList);

            if (radioGroup.getCheckedRadioButtonId()== R.id.rbt_rating) {
                Collections.sort(simpleParkingList, new Comparator<SimpleParking>() {
                    @Override
                    public int compare(SimpleParking o1, SimpleParking o2) {
                        if (o1.getType().equals(KeyWords.STREETPARKING) && o2.getType().equals(KeyWords.PRIVATEPARKING)) {
                            return -1;
                        } else if (o2.getType().equals(KeyWords.STREETPARKING) && o1.getType().equals(KeyWords.PRIVATEPARKING)) {
                            return 1;
                        }
                        return (int) ((Double.parseDouble(o1.getRating()) - Double.parseDouble(o2.getRating()))*(-1));
                    }
                });
            } else if (radioGroup.getCheckedRadioButtonId()== R.id.rbt_range) {
                Collections.sort(simpleParkingList, new Comparator<SimpleParking>() {
                    @Override
                    public int compare(SimpleParking o1, SimpleParking o2) {
                        if (o1.getType().equals(KeyWords.STREETPARKING) && o2.getType().equals(KeyWords.PRIVATEPARKING)) {
                            return -1;
                        } else if (o2.getType().equals(KeyWords.STREETPARKING) && o1.getType().equals(KeyWords.PRIVATEPARKING)) {
                            return 1;
                        }
                        Location o1Location = new Location("");
                        o1Location.setLatitude(Double.parseDouble(o1.getLatitude()));
                        o1Location.setLongitude(Double.parseDouble(o1.getLongitude()));
                        Location o2Location = new Location("");
                        o2Location.setLatitude(Double.parseDouble(o2.getLatitude()));
                        o2Location.setLongitude(Double.parseDouble(o2.getLongitude()));
                        if((location.distanceTo(o1Location)-location.distanceTo(o2Location))>0){
                            return 1;
                        }else{
                            return -1;
                        }
                    }
                });
            }

            parkingListView.showSimpleParkingList(simpleParkingList);
        }
    }


}
