package fr.upjv.asiprojet.tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddInscriptionTask {
    private final Handler handler;

    public AddInscriptionTask(Handler handler) {
        this.handler = handler;
    }

    public void execute(int idUser, int idCours) {
        new Thread(() -> {
            HttpURLConnection urlConnection = null;
            try {
                Log.d("AddInscriptionTask", "Executing task...");

                URL url = new URL("http://10.0.2.2:8080/api/inscriptions/ajouter");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Convertir l'objet InscriptionRequest en JSON
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("userId", idUser);
                jsonRequest.put("coursId", idCours);

                // Envoyer les données JSON à l'API
                try (OutputStream outputStream = urlConnection.getOutputStream()) {
                    byte[] input = jsonRequest.toString().getBytes("utf-8");
                    outputStream.write(input, 0, input.length);
                }

                // Obtenir le code d'état de la réponse
                int statusCode = urlConnection.getResponseCode();
                Log.d("AddInscriptionTask", "Response code: " + statusCode);

                // Lire la réponse JSON depuis le flux d'entrée de la connexion
                String response = readResponse(urlConnection.getInputStream());
                Log.d("AddInscriptionTask", "Response: " + response);

                // Envoyer le message au Handler
                Message message = Message.obtain();
                message.obj = statusCode;
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                message.setData(bundle);
                handler.sendMessage(message);


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e("AddInscriptionTask", "Error: " + e.getMessage());
            } finally {
                // Fermeture de la connexion HttpURLConnection
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }).start(); // Lancer le thread
    }
    private String readResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}
