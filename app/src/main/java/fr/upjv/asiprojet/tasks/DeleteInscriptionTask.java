package fr.upjv.asiprojet.tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteInscriptionTask {
    private final Handler handler;

    public DeleteInscriptionTask(Handler handler) {
        this.handler = handler;
    }

    public void execute(int idUser, int idCours) {
        new Thread(() -> {
            HttpURLConnection urlConnection = null;
            try {
                Log.d("DeleteInscriptionTask", "Executing task...");

                // Construire l'URL avec les paramètres idUser et idCours
                URL url = new URL("http://10.0.2.2:8080/api/inscriptions/supprimer/" + idUser + "/" + idCours);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");

                // Obtenir le code d'état de la réponse
                int statusCode = urlConnection.getResponseCode();
                Log.d("DeleteInscriptionTask", "Response code: " + statusCode);

                // Lire la réponse JSON depuis le flux d'entrée de la connexion
                String response = readResponse(urlConnection.getInputStream());
                Log.d("DeleteInscriptionTask", "Response: " + response);

                // Envoyer le message au Handler
                Message message = Message.obtain();
                message.obj = statusCode;
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                message.setData(bundle);
                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DeleteInscriptionTask", "Error: " + e.getMessage());
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