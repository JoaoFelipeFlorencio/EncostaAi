package encostai.encostai.com.br.encostaai.activity.rate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.models.Rating;

public class RateActivity extends AppCompatActivity implements IRateView{

    private IRatePresenter presenter;

    private TextView localName;
    private RatingBar rateBar;
    private EditText comment;
    private Button submit;

    private String localNameValue;
    private String localId;
    private String localType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Intent intent = getIntent();
        localNameValue = intent.getExtras().getString("name");
        localId = intent.getExtras().getString("id");
        localType = intent.getExtras().getString("type");

        localName = (TextView)findViewById(R.id.txt_local_name);
        rateBar = (RatingBar)findViewById(R.id.rtb_rate);
        comment = (EditText)findViewById(R.id.edt_comment);
        submit = (Button) findViewById(R.id.btn_submit);

        localName.setText(localNameValue);

        presenter = new RatePresenter(RateActivity.this,new RateInteractor(RateActivity.this));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRate();
            }
        });


    }


    private void submitRate(){
        int rate = (int) rateBar.getRating();
        presenter.submitRate(localType,localId,rate,comment.getText().toString());
    }

    @Override
    public void submitSucces() {
        Toast.makeText(this,"Avaliacao enviada com sucesso",Toast.LENGTH_LONG).show();
        finish();
    }
}
