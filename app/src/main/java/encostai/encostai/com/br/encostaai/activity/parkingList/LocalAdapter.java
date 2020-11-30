package encostai.encostai.com.br.encostaai.activity.parkingList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.models.SimpleParking;
import encostai.encostai.com.br.encostaai.utils.KeyWords;

class LocalAdapter extends ArrayAdapter<SimpleParking> {

    private ArrayList<SimpleParking> simpleParkingList;
    private Context context;

    public LocalAdapter(Context context, ArrayList<SimpleParking> objects) {
        super(context, 0, objects);
        this.context = context;
        this.simpleParkingList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _view = convertView;
        ViewHolder vh;

        if (simpleParkingList != null) {
            if (_view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                _view = inflater.inflate(R.layout.simple_parking_list, parent, false);
                vh = new ViewHolder();
                vh.parkName = (TextView) _view.findViewById(R.id.txt_name);
                vh.parkRating = (TextView) _view.findViewById(R.id.txt_rating);
                _view.setTag(vh);
            } else {
                vh = (ViewHolder) _view.getTag();
            }

            SimpleParking simpleParking = simpleParkingList.get(position);

            vh.parkName.setText(simpleParking.getName());
            vh.parkRating.setText(String.valueOf(simpleParking.getRating()));


            if (simpleParking.getType().equals(KeyWords.STREETPARKING)) {
                _view.setBackgroundResource(R.color.colorAccent);
            } else if (simpleParking.getType().equals(KeyWords.PRIVATEPARKING)){
                _view.setBackgroundResource(R.color.colorPrimaryDark);
            }
            Log.i("adapter", simpleParking.getId());
        }
        return _view;
    }

    private static class ViewHolder {
        public TextView parkName;
        public TextView parkRating;
    }
}

