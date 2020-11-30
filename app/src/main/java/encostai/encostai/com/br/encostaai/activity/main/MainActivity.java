package encostai.encostai.com.br.encostaai.activity.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.activity.parkDetail.ParkDetailActivity;
import encostai.encostai.com.br.encostaai.activity.parkingList.ParkingListActivity;
import encostai.encostai.com.br.encostaai.activity.favorites.FavoritesActivity;
import encostai.encostai.com.br.encostaai.activity.login.LoginActivity;
import encostai.encostai.com.br.encostaai.activity.main.Question.QuestionProvider;
import encostai.encostai.com.br.encostaai.activity.main.Question.QuestionTimer;
import encostai.encostai.com.br.encostaai.activity.profile.ProfileActivity;
import encostai.encostai.com.br.encostaai.activity.termsPolicies.TermsPoliciesActivity;
import encostai.encostai.com.br.encostaai.models.MyMarker;
import encostai.encostai.com.br.encostaai.utils.KeyWords;
import encostai.encostai.com.br.encostaai.utils.Permission;

public class MainActivity extends AppCompatActivity implements IMainView, OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //AppCompatActivity
    private GoogleMap mMap;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private List<MyMarker> myMarkers;

    private IMainPresenter presenter;
    private MainInteractor interactor;
    private Location location;
    private QuestionProvider questionProvider;
    private QuestionTimer questionTimer;
    private Boolean askQuestion = false;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Carregando ...",Toast.LENGTH_SHORT).show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Permission.permissionsValidate(1, this, permissions);

        interactor = new MainInteractor(this);
        presenter = new MainPresenter(this, interactor);

        myMarkers = new ArrayList<>();

        questionProvider = new QuestionProvider(this, interactor);
        questionTimer = new QuestionTimer(this);
        questionProvider.run();
        questionTimer.start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = presenter.onMapReady(this, googleMap);

        presenter.drawPrivatePark();
        presenter.drawStreetPark();

        goTo(new LatLng(-8.06301848, -34.8714073));

        presenter.getLocation();

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.remove();
                for(MyMarker myMarker:myMarkers){
                    if(marker.getId().equals(myMarker.getMarker().getId())){
                        replacecMarker(myMarker);
                        return;
                    }
                }
                Log.i("Flag","long");
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });



    }

    private void replacecMarker(MyMarker myMarker) {
        openParkDetail(myMarker);
        myMarkers.remove(myMarker);
        if(myMarker.getType().equals(KeyWords.STREETPARKING)){
            myMarkers.add(new MyMarker(myMarker.getId(),myMarker.getLatLng(),myMarker.getType(), mMap.addMarker(new MarkerOptions().position(myMarker.getLatLng()).alpha(myMarker.getMarker().getAlpha()).title(myMarker.getMarker().getTitle()).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))));

        }else {
            myMarkers.add(new MyMarker(myMarker.getId(), myMarker.getLatLng(), myMarker.getType(), mMap.addMarker(new MarkerOptions().position(myMarker.getLatLng()).alpha(myMarker.getMarker().getAlpha()).title(myMarker.getMarker().getTitle()).draggable(true))));
        }
    }

    private void openParkDetail(MyMarker myMarker) {
        Intent intent = new Intent(MainActivity.this, ParkDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",myMarker.getId());
        bundle.putString("type",myMarker.getType());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Navegação lateral
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_Profile) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_ParkList) {
            intent = new Intent(MainActivity.this, ParkingListActivity.class);
            if (location != null) {
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", this.location.getLatitude());
                bundle.putDouble("longitude", this.location.getLongitude());
                intent.putExtras(bundle);
            }
            startActivity(intent);

        } else if (id == R.id.nav_Favorites) {
            intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Payment) {


        } else if (id == R.id.nav_TermsPolicies) {
            intent = new Intent(MainActivity.this, TermsPoliciesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_SignOut) {
            presenter.signOut();
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void addMarker(String id,LatLng latLng, String tittle) {
        myMarkers.add(new MyMarker(id,latLng, KeyWords.PRIVATEPARKING,mMap.addMarker(new MarkerOptions().position(latLng).alpha((float) 0.75).title(tittle).draggable(true))));
    }

    @Override
    public void addLine(String id, LatLng latLng1, LatLng latLng2, String tittle) {
        mMap.addPolyline(new PolylineOptions()
                .add(latLng1, latLng2)
                .width(25)
                .color(Color.BLUE)
                .geodesic(true));
        LatLng finalLatLng = new LatLng((latLng1.latitude+latLng2.latitude)/2,(latLng1.longitude+latLng2.longitude)/2);
        myMarkers.add(new MyMarker(id,finalLatLng,KeyWords.STREETPARKING,mMap.addMarker(new MarkerOptions().position(finalLatLng).alpha((float) 0.75).title(tittle).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))));
    }

    @Override
    public void goTo(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }

    @Override
    public void newLocation(Location location) {
        this.location = location;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionProvider.destroyManager();
        questionTimer.killTimer();
    }

    public synchronized void askQuestion(final String id) {
        if(askQuestion){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Há vagas na sua localização?").setCancelable(false).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Boolean answer = false;
                    presenter.sendAnswerToInte(answer, id);
                }
            }).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Boolean answer = true;
                    presenter.sendAnswerToInte(answer, id);
                }
            });
            alert.create();
            alert.show();
            setAskQuestionFalse();
        }
    }

    @Override
    public void finishLoading() {
        Toast.makeText(this,"Mapa Atualizado",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void clearMarkers(){
        mMap.clear();
        myMarkers.clear();
    }


    public Boolean getAskQuestion (){
        return this.askQuestion;
    }

    public void setAskQuestionTrue(){
        Log.i("Flag","ask question true");
        this.askQuestion = true;
    }

    public void setAskQuestionFalse(){
        Log.i("Flag","ask question false");
        this.askQuestion =false;
    }
}