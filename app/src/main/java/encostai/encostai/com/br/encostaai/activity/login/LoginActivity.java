package encostai.encostai.com.br.encostaai.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import encostai.encostai.com.br.encostaai.R;
import encostai.encostai.com.br.encostaai.activity.signUp.SignUpActivity;
import encostai.encostai.com.br.encostaai.activity.main.MainActivity;
import encostai.encostai.com.br.encostaai.models.Rating;
import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {


    private ILoginPresenter presenter;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editLogin);
        password = findViewById(R.id.editSenha);
        presenter = new LoginPresenter(this, new LoginInteractor());
        isUserLogged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.entrar:
                presenter.userLogin(email.getText().toString(), password.getText().toString());
                break;
            case R.id.cadastrar:
                presenter.userRegister();
                break;
        }
    }

    public void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void setMessage(String message) {

        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

    }

    private void isUserLogged() {
        presenter.isUserLogged();
    }


}
