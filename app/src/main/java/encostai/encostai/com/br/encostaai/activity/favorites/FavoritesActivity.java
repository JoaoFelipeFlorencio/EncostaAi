package encostai.encostai.com.br.encostaai.activity.favorites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.activity.parkDetail.ParkDetailActivity;
import encostai.encostai.com.br.encostaai.activity.parkingList.ParkingListActivity;
import encostai.encostai.com.br.encostaai.models.SimpleParking;
import encostai.encostai.com.br.encostaai.models.User;

public class FavoritesActivity extends AppCompatActivity implements IFavoritesView{

    private ListView listView;
    private ArrayList<SimpleParking> favoritesList;
    private ArrayAdapter<SimpleParking> adapter;
    private IFavoritesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toast.makeText(this,"Carregando ...",Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        listView = (ListView) findViewById(R.id.lv_favorites);
        favoritesList = new ArrayList<SimpleParking>();
        adapter = new FavoritesAdapter(FavoritesActivity.this, favoritesList);
        listView.setAdapter(adapter);

        presenter = new FavoritesPresenter(this,new FavoritesInteractor(this));

        presenter.getFavorites();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Flag","toch");
                openDetailActivity(position);
            }
        });
        Toast.makeText(this,"Lista atualizada",Toast.LENGTH_SHORT).show();

    }


    @Override
    public synchronized void showFavoriteSimpleList(ArrayList<SimpleParking> favoriteSimpleList){
        if(favoriteSimpleList.isEmpty()){
            Toast.makeText(this,"Voce nao tem favoritos",Toast.LENGTH_LONG).show();
        }else {
            this.favoritesList.clear();
            adapter.notifyDataSetChanged();
            this.favoritesList.addAll(favoriteSimpleList);
            adapter.notifyDataSetChanged();
        }
    }

    private void openDetailActivity(int position) {
        Log.i("Flag","open");
        Intent intent = new Intent(FavoritesActivity.this, ParkDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",favoritesList.get(position).getId());
        bundle.putString("type",favoritesList.get(position).getType());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void confirmRemoveFavorite(final String parkId){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Remover de favoritos?").setCancelable(false).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.rmFavorite(parkId);
                recreate();
            }
        });
        alert.create();
        alert.show();

    }
}
