package fr.upjv.asiprojet;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
private EditText username ;
private EditText password;

private static final String TAG = "LoginActivity"; // Definir TAG pour cette class

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean success = (boolean) msg.obj;
            // En fonction de la réponse, prendre les mesures appropriées
            if (success) {
                // Connexion réussie
                Log.d(TAG, "fonctionne");
            } else {
                // Échec de la connexion
                Log.e(TAG, "fonctionne pas");
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        // Exemple d'utilisation : déclencher la tâche de connexion depuis un bouton
        findViewById(R.id.button2).setOnClickListener(v -> {
            username = findViewById(R.id.editTextText2);
            password = findViewById(R.id.editTextTextPassword5);

            // Démarrer la tâche de connexion avec le Handler
            new LoginTask(username.getText().toString(), password.getText().toString(), handler).execute();
        });

        

    }
//deuxieme bouton
   /* public void pageSuivante(View view) {

        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }*/
    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }/*
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

}