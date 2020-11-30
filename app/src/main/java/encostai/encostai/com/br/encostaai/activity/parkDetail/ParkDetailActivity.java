package encostai.encostai.com.br.encostaai.activity.parkDetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.activity.rate.RateActivity;
import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.utils.KeyWords;

public class ParkDetailActivity extends AppCompatActivity implements IParkDetailView {

    private String parkId;
    private String parkType;
    private TextView parkName;
    private TextView parkDescription;
    private TextView parkRating;
    private ImageButton parkFavorite;
    private Button rate;

    private TextView parkEmptySpaces;
    private TextView parkProbability;

    private IParkDetailPresenter presenter;

    private boolean isFavorite = false;
    private ListView listView;
    private ArrayList<Rating> ratingsList;
    private ArrayAdapter<Rating> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        parkId = intent.getExtras().getString("id");
        parkType = intent.getExtras().getString("type");
        Log.i("Flag", parkId);
        Log.i("Flag", parkType);

        presenter = new ParkDetailPresenter(this, new ParkDetailInteractor(this));

        switch (parkType) {
            case KeyWords.STREETPARKING:
                onStreetPark();
                break;
            case KeyWords.PRIVATEPARKING:
                onPrivatePark();
                break;
            default:
                break;
        }

    }


    private void onStreetPark() {
        setContentView(R.layout.activity_park_detail_street);
        parkName = (TextView) findViewById(R.id.txt_name);
        parkDescription = (TextView) findViewById(R.id.txt_description);
        parkRating = (TextView) findViewById(R.id.txt_rating);
        parkFavorite = (ImageButton) findViewById(R.id.img_is_favorite);
        parkProbability = (TextView) findViewById(R.id.txt_probability);
        listView = (ListView) findViewById(R.id.lv_coments);
        rate = (Button) findViewById(R.id.btn_rate);

        ratingsList = new ArrayList<Rating>();
        adapter = new RatingAdapter(ParkDetailActivity.this,ratingsList);
        listView.setAdapter(adapter);

        presenter.getStreetParkDetail(parkId);

        activeButton();

    }



    @Override
    public void onStreetRecived(String name, String description, double rating, final boolean favorite, double probability) {
        parkName.setText(name);
        parkDescription.setText(description);
        parkRating.setText(String.valueOf(rating));
        parkProbability.setText(String.valueOf(probability));
        isFavorite = favorite;
        if(favorite){
            parkFavorite.setBackgroundResource(R.drawable.ic_star_black_24dp);

        }else {
            parkFavorite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        }

    }

    @Override
    public void onPrivateRecived(String name, String description, double rating, boolean favorite, int emptySpace) {
        parkName.setText(name);
        parkDescription.setText(description);
        parkRating.setText(String.valueOf(rating));
        parkEmptySpaces.setText(String.valueOf(emptySpace));
        isFavorite = favorite;
        if(favorite){
            parkFavorite.setBackgroundResource(R.drawable.ic_star_black_24dp);

        }else {
            parkFavorite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    @Override
    public void showRatings(ArrayList<Rating> ratingList) {
        this.ratingsList.clear();
        adapter.notifyDataSetChanged();
        this.ratingsList.addAll(ratingList);
        adapter.notifyDataSetChanged();

    }

    private void onPrivatePark() {
        setContentView(R.layout.activity_park_detail_private);
        parkName = (TextView) findViewById(R.id.txt_name);
        parkDescription = (TextView) findViewById(R.id.txt_description);
        parkRating = (TextView) findViewById(R.id.txt_rating);
        parkFavorite = (ImageButton) findViewById(R.id.img_is_favorite);
        parkEmptySpaces = (TextView) findViewById(R.id.txt_empty_spot);
        listView = (ListView) findViewById(R.id.lv_coments);
        rate = (Button) findViewById(R.id.btn_rate);

        ratingsList = new ArrayList<Rating>();
        adapter = new RatingAdapter(ParkDetailActivity.this,ratingsList);
        listView.setAdapter(adapter);

        presenter.getPrivateParkDetail(parkId);

        activeButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void activeButton() {
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRate();
            }
        });

        parkFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite){
                    confirmRemoveFavorite();
                }else{
                    confirmAddFavorite();

                }
            }
        });
    }

    private void goToRate(){
        Intent intent = new Intent(ParkDetailActivity.this, RateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",parkName.getText().toString());
        bundle.putString("id",parkId);
        bundle.putString("type",parkType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void confirmAddFavorite(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Adicionar a favoritos?").setCancelable(false).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.addFavorite(parkId);
                parkFavorite.setBackgroundResource(R.drawable.ic_star_black_24dp);
                isFavorite = true;
            }
        });
        alert.create();
        alert.show();

    }

    private void confirmRemoveFavorite(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Remover de favoritos?").setCancelable(false).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.rmFavorite(parkId);
                parkFavorite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                isFavorite = false;
            }
        });
        alert.create();
        alert.show();

    }
}


