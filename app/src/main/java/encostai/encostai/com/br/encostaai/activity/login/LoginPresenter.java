package encostai.encostai.com.br.encostaai.activity.login;


public class LoginPresenter implements ILoginPresenter, ILoginInteractor.LoginListener {

    private ILoginView loginView;
    private ILoginInteractor loginInteractor;

    LoginPresenter(ILoginView loginView, LoginInteractor loginInteractor) {

        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    public void userLogin(String email, String password) {

        //loginView.goToMain();
        loginInteractor.userLogin(email, password, this, (LoginActivity) loginView);

    }

    public void userRegister() {

        loginView.goToSignUp();
    }

    public void onInputError(int errorType) {

        switch (errorType) {

            case 0:
                loginView.setMessage("Error: Campo em branco");
                break;
            case 1:
                loginView.setMessage("Error: Não é um e-mail valido");
                break;
            case 2:
                loginView.setMessage("Error: E-mail ou senha incorretos");
                break;
            case 3:
                loginView.setMessage("Error: Erro ao tentar logar!");
                break;

        }

    }

    public void onSucess() {
        loginView.goToMain();
    }

    public void isUserLogged() {
        loginInteractor.isUserLogged(this);
    }

}
