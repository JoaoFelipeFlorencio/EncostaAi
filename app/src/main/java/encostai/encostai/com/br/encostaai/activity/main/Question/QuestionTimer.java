package encostai.encostai.com.br.encostaai.activity.main.Question;

import android.util.Log;
import android.widget.Toast;

import encostai.encostai.com.br.encostaai.activity.main.MainActivity;

public class QuestionTimer extends Thread {

    private MainActivity mainActivity;
    private boolean keepLiving = true;


    public QuestionTimer(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void killTimer() {
        keepLiving = false;
    }


    @Override
    public void run() {

        while (keepLiving) {

            if (!mainActivity.getAskQuestion()) {
                //Thread, quando encontra a flag false, dorme por 30 minutos antes de altera-lá.
                try {
                    Log.i("Flag","start of sleep");
                    sleep(60000);
                    Log.i("Flag","end of sleep");
                    mainActivity.setAskQuestionTrue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
