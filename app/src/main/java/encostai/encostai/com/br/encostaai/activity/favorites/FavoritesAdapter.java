package encostai.encostai.com.br.encostaai.activity.favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.models.SimpleParking;
import encostai.encostai.com.br.encostaai.utils.KeyWords;

public class FavoritesAdapter extends ArrayAdapter<SimpleParking> {

    private FavoritesActivity context;
    private ArrayList<SimpleParking> favoritesList;

    public FavoritesAdapter(FavoritesActivity context, ArrayList<SimpleParking> objects) {
        super(context, 0, objects);
        this.context = context;
        this.favoritesList = objects;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View _view = convertView;
        ViewHolder vh;

        if (favoritesList != null) {
            if (_view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                _view = inflater.inflate(R.layout.favorite_list,parent,false);
                vh = new ViewHolder();
                vh.favoriteName = (TextView) _view.findViewById(R.id.txt_favorite_name);
                vh.isFavorite = (ImageButton) _view.findViewById(R.id.img_is_favorite);
                vh.isFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.confirmRemoveFavorite(favoritesList.get(position).getId());
                    }
                });
                _view.setTag(vh);
            }else{
                vh = (ViewHolder) _view.getTag();
            }

            SimpleParking simpleParking = favoritesList.get(position);

            vh.favoriteName.setText(simpleParking.getName());
            vh.isFavorite.setBackgroundResource(R.drawable.ic_star_black_24dp);

            if (simpleParking.getType().equals(KeyWords.STREETPARKING)) {
                _view.setBackgroundResource(R.color.colorAccent);
            } else if (simpleParking.getType().equals(KeyWords.PRIVATEPARKING)){
                _view.setBackgroundResource(R.color.colorPrimaryDark);
            }
        }
        return _view;
    }

    private static class ViewHolder {
        public TextView favoriteName;
        public ImageButton isFavorite;
    }


}
