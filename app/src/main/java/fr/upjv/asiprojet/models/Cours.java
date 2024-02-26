package fr.upjv.asiprojet.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cours {
    private int idCours;
    private String nomCours;
    private LocalDateTime horaire;
    private String lieu;
    private String description;
    private String instructeur;

    public Cours(int id, String nc, LocalDateTime h, String li, String desc, String inst) {
        idCours = id;
        nomCours = nc;
        horaire = h;
        lieu = li;
        description = desc;
        instructeur = inst;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public LocalDateTime getHoraire() {
        return horaire;
    }

    public void setHoraire(LocalDateTime horaire) {
        this.horaire = horaire;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructeur() {
        return instructeur;
    }

    public void setInstructeur(String instructeur) {
        this.instructeur = instructeur;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + idCours +
                ", nomCours='" + nomCours + '\'' +
                ", horaire=" + horaire +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", instructeur='" + instructeur + '\'' +
                '}';
    }

    public String toStringSansDetailles() {
        return "Cours - " +
                + idCours +
                "\nNom : " + nomCours + "\nDate : " +
                horaire.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                "\nHeure: " + horaire.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String toStringDetaille() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID du cours : ").append(idCours).append("\n");
        stringBuilder.append("Nom du cours : ").append(nomCours).append("\n");
        stringBuilder.append("Horaire : ").append(horaire.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
        stringBuilder.append("Horaire : ").append(horaire.format(DateTimeFormatter.ofPattern("HH:mm"))).append("\n");
        stringBuilder.append("Lieu : ").append(lieu).append("\n");
        stringBuilder.append("Description : ").append(description).append("\n");
        stringBuilder.append("Instructeur : ").append(instructeur);
        return stringBuilder.toString();
    }
}