package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;

import fr.upjv.asiprojet.tasks.DeleteInscriptionTask;

public class DesinscriptionsActivity extends AppCompatActivity {
    private int idUser;
    private String nom;
    private int idCours;
    private String cours;
    private TextView textView;


    // Handler pour gérer la réponse de la tâche DeleteInscriptionTask
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            // Vérifier le code de statut de la réponse
            int statusCode = (int) msg.obj; // Cast the message object to int
            if (statusCode == HttpURLConnection.HTTP_OK) {
                // La réponse est OK, récupérer la réponse de l'API et l'afficher
                String response = (String) msg.getData().get("response"); // Get response from message data
                Toast.makeText(DesinscriptionsActivity.this, response, Toast.LENGTH_SHORT).show();
            } else {
                // La réponse n'est pas OK, afficher un message d'erreur
                Toast.makeText(DesinscriptionsActivity.this, "Erreur lors de l'ajout de l'inscription", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desinscriptions);

        this.textView = findViewById(R.id.textView7);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");
        this.idCours = intent.getIntExtra("idCours", -1);
        this.cours = intent.getStringExtra("Cours");

        // Print valeurs sur la logconsole
        Log.d(TAG, "ID: " + idUser + ", Nom: " + nom + ", ID Cours: " + idCours + ", Cours: " + cours);
        textView.setText(cours); // Définir le texte du TextView avec la valeur cours


    }

    // Passer a la page MesInscriptions
    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, ListCoursActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }

    public void onClickDesinscription(View view) {
        // Appel de la méthode pour supprimer l'inscription
        new DeleteInscriptionTask(handler).execute(idUser, idCours);
    }
}

