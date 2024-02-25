package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;

import fr.upjv.asiprojet.tasks.AddInscriptionTask;
import fr.upjv.asiprojet.tasks.ListCoursTask;

public class InscriptionsActivity extends AppCompatActivity {
    private int idUser;
    private String nom;
    private int idCours;
    private String cours;
    private TextView textView;
    private Button button;

    // Handler pour gérer la réponse de la tâche AddInscriptionTask
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            // Vérifier le code de statut de la réponse
            int statusCode = (int) msg.obj; // Cast the message object to int
            if (statusCode == HttpURLConnection.HTTP_OK) {
                // La réponse est OK, récupérer la réponse de l'API et l'afficher
                String response = (String) msg.obj; // Get response from message data
                Toast.makeText(InscriptionsActivity.this, response, Toast.LENGTH_SHORT).show();
            } else {
                // La réponse n'est pas OK, afficher un message d'erreur
                Toast.makeText(InscriptionsActivity.this, "Erreur lors de l'ajout de l'inscription", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscriptions);

        this.textView = findViewById(R.id.textView);
        this.button = findViewById(R.id.button4);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");
        this.idCours = intent.getIntExtra("idCours", -1);
        this.cours = intent.getStringExtra("Cours");

        textView.setText(cours); // Définir le texte du TextView avec la valeur cours
    }

    // Passer a la page ListCours
    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, ListCoursActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }

    public void onClickInscription(View view) {
        // Appel de la méthode pour ajouter l'inscription
        new AddInscriptionTask(handler).execute(idUser, idCours);
    }
}