package encostai.encostai.com.br.encostaai.activity.parkDetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.models.Rating;

public class RatingAdapter extends ArrayAdapter<Rating> {

    private ArrayList<Rating> ratingList;
    private Context context;

    public RatingAdapter(Context context, ArrayList<Rating> objects) {
        super(context, 0, objects);
        this.ratingList = objects;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _view = convertView;
        RatingAdapter.ViewHolder vh;

        if (ratingList != null) {
            if (_view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                _view = inflater.inflate(R.layout.rating_list, parent, false);
                vh = new ViewHolder();
                vh.userName = (TextView) _view.findViewById(R.id.txt_user_name);
                vh.rating = (TextView) _view.findViewById(R.id.txt_rating);
                vh.comment = (TextView) _view.findViewById(R.id.txt_comment);
                _view.setTag(vh);
            } else {
                vh = (RatingAdapter.ViewHolder) _view.getTag();
            }

            Rating rating = ratingList.get(position);

            vh.userName.setText(rating.getUserName());
            vh.rating.setText(String.valueOf(rating.getRate()));
            vh.comment.setText(rating.getDescription());

            Log.i("adapter rating", rating.getId() + rating.getUserName() + rating.getDescription() + rating.getRate());
        }
        return _view;
    }

    private static class ViewHolder {
        public TextView userName;
        public TextView rating;
        public TextView comment;
    }
}
