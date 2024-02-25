package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DesinscriptionsActivity extends AppCompatActivity {
    private int idUser;
    private String nom;
    private int idCours;
    private String cours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desinscriptions);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");
        this.idCours = intent.getIntExtra("idCours", -1);
        this.cours = intent.getStringExtra("Cours");

        // Print valeurs sur la logconsole
        Log.d(TAG, "ID: " + idUser + ", Nom: " + nom + ", ID Cours: " + idCours + ", Cours: " + cours);
    }

    // Passer a la page MesInscriptions
    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, ListCoursActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }
}