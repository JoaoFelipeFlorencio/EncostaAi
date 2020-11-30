package encostai.encostai.com.br.encostaai.activity.profile;

import encostai.encostai.com.br.encostaai.utils.Preferences;

public interface IProfilePresenter {

    void changeUserInfo(String name, boolean exposure, String password, String newPassword);
}
