package encostai.encostai.com.br.encostaai.activity.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.activity.login.LoginActivity;
import encostai.encostai.com.br.encostaai.activity.main.MainActivity;
import encostai.encostai.com.br.encostaai.utils.Connection;

public class SignUpActivity extends AppCompatActivity implements ISignUpView, View.OnClickListener {


    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmationPassword;
    private ISignUpPresenter presenter;
    private Switch exposure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editNome);
        email = findViewById(R.id.editNovoLogin);
        password = findViewById(R.id.editNovaSenha);
        confirmationPassword = findViewById(R.id.editConfirNovaSenha);
        exposure = findViewById(R.id.ExposureButton);
        presenter = new SignUpPresenter(this, new SignUpInteractor());
    }


    @Override
    public void onClick(View v) {

        presenter.registerUser(name.getText().toString(), email.getText().toString(), password.getText().toString(), confirmationPassword.getText().toString(), exposure.isChecked());

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    public void setMessage(String message) {

        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
