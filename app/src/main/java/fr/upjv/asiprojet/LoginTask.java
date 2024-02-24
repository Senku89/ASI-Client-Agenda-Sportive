package fr.upjv.asiprojet;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    private final String username;
    private final String password;
    private final Handler handler;

    public LoginTask(String username, String password, Handler handler) {
        this.username = username;
        this.password = password;
        this.handler = handler;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://10.0.2.2:8080/api/login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lire la réponse JSON depuis le flux d'entrée de la connexion
            try (InputStream inputStream = urlConnection.getInputStream()) {
                // Analyser la réponse JSON
                JSONObject jsonResponse = new JSONObject(new Scanner(inputStream).useDelimiter("\\A").next());

                // Extraire la valeur du champ "success" de l'objet JSON
                return jsonResponse.getBoolean("success");
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean jsonResponse) {
        // Envoie du résultat au thread principal via le Handler
        Message message = handler.obtainMessage();
        message.obj = jsonResponse;
        handler.sendMessage(message);

    }

}