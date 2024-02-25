package fr.upjv.asiprojet.tasks;

import android.os.Handler;
import android.os.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginTask {
    private final String username;
    private final String password;
    private final Handler handler;

    public LoginTask(String username, String password, Handler handler) {
        this.username = username;   // Nom d'utilisateur
        this.password = password;   // Mot de passe
        this.handler = handler;     // Handler pour communiquer avec l'UI thread
    }

    // Méthode pour exécuter la tâche de connexion
    public void execute() {
        // Créer un Executor pour exécuter la tâche sur un thread séparé
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    // URL pour l'api de connexion
                    URL url = new URL("http://10.0.2.2:8080/api/login");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);

                    // Créer une chaîne JSON pour les informations d'identification
                    String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
                    try (OutputStream os = urlConnection.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Obtenir le code d'état de la réponse
                    int statusCode = urlConnection.getResponseCode();

                    // Lire la réponse JSON depuis le flux d'entrée de la connexion
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        try (InputStream inputStream = urlConnection.getInputStream()) {
                            // Envoyer la réponse JSON au thread principal via le Handler
                            JSONObject jsonResponse = new JSONObject(new Scanner(inputStream).useDelimiter("\\A").next());
                            handler.sendMessage(Message.obtain(handler, 0, jsonResponse));
                        }
                    } else if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        // Envoyer un message d'erreur à l'activité principale
                        handler.sendMessage(handler.obtainMessage());
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    // Fermeture de la connexion HttpURLConnection
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        });
    }
}