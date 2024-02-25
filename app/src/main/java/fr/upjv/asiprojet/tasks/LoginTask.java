package fr.upjv.asiprojet;

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
        this.username = username;
        this.password = password;
        this.handler = handler;
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

                    // Lire la réponse JSON depuis le flux d'entrée de la connexion
                    try (InputStream inputStream = urlConnection.getInputStream()) {
                        // Envoyer la réponse JSON au thread principal via le Handler
                        JSONObject jsonResponse = new JSONObject(new Scanner(inputStream).useDelimiter("\\A").next());
                        handler.sendMessage(Message.obtain(handler, 0, jsonResponse));
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        });
    }
}