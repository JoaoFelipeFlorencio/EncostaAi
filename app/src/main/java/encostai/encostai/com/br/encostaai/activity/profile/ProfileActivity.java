package encostai.encostai.com.br.encostaai.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.utils.Preferences;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, IProfileView {

    private EditText name;
    private TextView email;
    private EditText password;
    private EditText newPassword;
    private EditText confirmPassword;
    private Switch exposure;
    private IProfilePresenter profilePresenter;

    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.editText5);
        email = findViewById(R.id.textViewEmail);
        password = findViewById(R.id.editText2);
        newPassword = findViewById(R.id.editText3);
        confirmPassword = findViewById(R.id.editText4);
        exposure = findViewById(R.id.switch1);
        profilePresenter = new ProfilePresenter(this, new ProfileInteractor(this));
        preferences = new Preferences(this);
        name.setHint(preferences.getName());
        email.setText(preferences.getEmail());
        exposure.setChecked(preferences.getExposure());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.button2:

                if(!password.getText().toString().isEmpty()) {
                    if (verifyPassword(newPassword.getText().toString(), confirmPassword.getText().toString())) {
                        profilePresenter.changeUserInfo(name.getText().toString(), exposure.isChecked(), password.getText().toString(), newPassword.getText().toString());
                    } else {
                        Toast.makeText(this, "Senhas precisam ser iguais", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(this, "E necessario da senha para alterar informacoes", Toast.LENGTH_LONG).show();
                }
                break;
        }


    }

    public void setMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void restart() {
        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean verifyPassword(String password1, String password2){
        return password1.equals(password2);

    }

}
