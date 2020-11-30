package encostai.encostai.com.br.encostaai.activity.profile;

import encostai.encostai.com.br.encostaai.models.User;
import encostai.encostai.com.br.encostaai.utils.Preferences;

public interface IProfileInteractor {

    interface ProfileListener {

        void onActionResponse(int messageType);

        void onSucess();
    }

    void changeInfo(User user, String password, ProfileListener profileListener);

}
