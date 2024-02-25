package fr.upjv.asiprojet;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import fr.upjv.asiprojet.tasks.LoginTask;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button button;

    private static final String TAG = "LoginActivity"; // Definir TAG pour cette class

    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JSONObject jsonResponse = (JSONObject) msg.obj;

            if (jsonResponse != null) {
                try {
                    // Extraire le champ "success" de la reponse JSON
                    boolean success = jsonResponse.getBoolean("success");

                    // Extraire le id et le nom
                    int id = jsonResponse.getInt("id");
                    String nom = jsonResponse.getString("nom");

                    // Affichage console
                    Log.d(TAG, "ID: " + id + ", Nom: " + nom);

                    if (success) {
                        Log.d(TAG, "Connexion réussie : Utilisateur connecté");
                        pageSuivante(button, id, nom);
                    } else {
                        Log.e(TAG, "Échec de la connexion : Nom d'utilisateur ou mot de passe incorrect");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Réponse JSON vide");
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        button = findViewById(R.id.button2);

        // Exemple d'utilisation : déclencher la tâche de connexion depuis un bouton
        findViewById(R.id.button2).setOnClickListener(v -> {
            username = findViewById(R.id.editTextText2);
            password = findViewById(R.id.editTextTextPassword5);

            // Démarrer la tâche de connexion avec le Handler
            new LoginTask(username.getText().toString(), password.getText().toString(), handler).execute();
        });


    }

    //Deuxieme bouton
    public void pageSuivante(View view, int id, String nom) {
        Intent intent = new Intent(this, ListCoursActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }

    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

//A SUPPRIMER
    /*
    public void login() {

    HttpURLConnection urlConnection = null;
    try {
        URL url = new URL("https://127.0.0.1:8080/api/login");
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setDoOutput(true);

        String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        try (OutputStream os = urlConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            String responseBody = response.toString();

            // Analyser la réponse JSON
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Extraire la valeur du champ "success" de l'objet JSON
            boolean success = jsonResponse.getBoolean("success");

            // En fonction de la réponse, prendre les mesures appropriées
            if (success) {
                // Connexion réussie
                Log.d(TAG,"fonctionne");
            } else {
                // Échec de la connexion
                Log.e(TAG,"fonctionne pas");
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }

    }*/