package fr.upjv.asiprojet.models;

public class UserInfo {
    int idUser;
    String nom;

    public UserInfo(int idUser, String nom) {
        this.idUser = idUser;
        this.nom = nom;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                '}';
    }
}
