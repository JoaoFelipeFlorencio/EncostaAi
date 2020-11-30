package encostai.encostai.com.br.encostaai.activity.login;

public interface ILoginPresenter {

    void userLogin(String email, String password);

    void userRegister();

    void isUserLogged();
}
