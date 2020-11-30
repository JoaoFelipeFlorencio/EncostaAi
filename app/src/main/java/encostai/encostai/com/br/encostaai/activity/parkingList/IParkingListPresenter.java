package encostai.encostai.com.br.encostaai.activity.parkingList;

import android.widget.RadioGroup;

public interface IParkingListPresenter {

    void joinParkingList();

    void getParkingList(boolean checked, boolean checked1, RadioGroup sortRadioGrouop);
}
