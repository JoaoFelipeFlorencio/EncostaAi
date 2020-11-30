package encostai.encostai.com.br.encostaai.activity.signUp;


public class SignUpPresenter implements ISignUpPresenter, SignUpInteractor.SignUpListener {

    private ISignUpView signUpView;
    private ISignUpInteractor signUpInteractor;

    SignUpPresenter(ISignUpView signUpView, SignUpInteractor signUpInteractor) {

        this.signUpView = signUpView;
        this.signUpInteractor = signUpInteractor;
    }


    public void registerUser(String name, String email, String password, String confirmPassword, boolean exposure) {


        signUpInteractor.addUser(name, email, password, confirmPassword, this, (SignUpActivity) signUpView, exposure);

    }

    public void onInputError(int errorType) {

        switch (errorType) {

            case 0:
                signUpView.setMessage("Error: Campo em branco");
                break;
            case 1:
                signUpView.setMessage("Error: Email já cadastrado");
                break;
            case 2:
                signUpView.setMessage("Error: Não é um e-mail valido");
                break;
            case 3:
                signUpView.setMessage("Error: Senha Fraca");
                break;
            case 4:
                signUpView.setMessage("Error: Senha e corfimar senha são diferentes");
                break;
            case 5:
                signUpView.setMessage("Error: Erro ao efetuar o cadastro!");
                break;
        }


    }

    public void onSucess() {
        signUpView.goToMain();
    }


}
