package encostai.encostai.com.br.encostaai.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private final String FILE_NAME = "encostaAi.preferences";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String INDENTIFIER_KEY = "logedUserIdentifier";
    private final String NAME_KEY = "logedUserName";
    private final String EMAIL_KEY = "logedUserEmail";
    private final String FAVORITES_KEY = "logedUserFavorites";
    private final String EXPOSURE_KEY = "exposure";

    public Preferences(Context context) {
        this.context = context;
        preferences = this.context.getSharedPreferences(FILE_NAME, MODE);
        editor = preferences.edit();
    }

    public void saveData(String userIdentifier, String userName, String userEmail, ArrayList<String> favorites, boolean exposure) {
        editor.putString(INDENTIFIER_KEY, userIdentifier);
        editor.putString(NAME_KEY, userName);
        editor.putString(EMAIL_KEY, userEmail);
        editor.putStringSet(FAVORITES_KEY, new HashSet<String>(favorites));
        editor.putBoolean(EXPOSURE_KEY,exposure);
        editor.commit();
    }

    public String getIdentifier() {
        return preferences.getString(INDENTIFIER_KEY, null);
    }

    public String getName() {
        return preferences.getString(NAME_KEY, null);
    }

    public String getEmail() {
        return preferences.getString(EMAIL_KEY, null);
    }

    public ArrayList<String> getFavorites(){
        return new ArrayList<>(preferences.getStringSet(FAVORITES_KEY,null));
    }

    public boolean getExposure(){
        return preferences.getBoolean(EXPOSURE_KEY,false);
    }

    public void clearData(){
        editor.clear();
    }




}
