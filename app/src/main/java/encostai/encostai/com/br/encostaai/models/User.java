package encostai.encostai.com.br.encostaai.models;
/*
 * Esta classe contempla o modelo de um Usuário e suas variáveis são:
 * Nome
 * Senha
 * Email
 * Exposicao -> serve apenas para identificar se o usuário quer ser reconhecido nas avaliações ou não
 *
 * ID -> é um dado que é obtido no banco. Ele é mostrado ao cliente apenas como controle.
 *
 * No fim há dois métodos onde o primeiro verifica a existência uso do e-mail
 * */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

import encostai.encostai.com.br.encostaai.utils.FirebaseConfig;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private boolean exposure;
    private ArrayList<String> favorites;

    public User() {
    }

    public User(String name, String password, String email, boolean exposure) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.exposure = exposure;
    }

    public void save(){
        DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference();
        databaseReference.child("users").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getExposure() {
        return exposure;
    }

    public void setExposure(boolean exposure) {
        this.exposure = exposure;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
}
