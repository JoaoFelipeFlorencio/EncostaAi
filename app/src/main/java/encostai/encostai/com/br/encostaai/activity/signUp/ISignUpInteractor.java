package encostai.encostai.com.br.encostaai.activity.signUp;

public interface ISignUpInteractor {

    interface SignUpListener {

        void onInputError(int errorType);

        void onSucess();

    }

    void addUser(String name, String email, String password, String confirmPassword, SignUpListener listener, SignUpActivity signUpView, boolean exposure);


}
