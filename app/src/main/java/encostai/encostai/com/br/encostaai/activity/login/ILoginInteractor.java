package encostai.encostai.com.br.encostaai.activity.login;

public interface ILoginInteractor {

    interface LoginListener {

        void onInputError(int errorType);

        void onSucess();

    }

    void userLogin(String email, String password, LoginListener listener, LoginActivity loginActivity);

    void isUserLogged(LoginListener listener);

}
