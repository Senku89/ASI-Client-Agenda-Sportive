package fr.upjv.asiprojet.tasks;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.upjv.asiprojet.models.Cours;

public class ListCoursTask {
    private Handler handler;

    public ListCoursTask(Handler handler) {
        this.handler = handler;     // Handler pour communiquer avec l'UI thread
    }

    // Méthode pour chercher la liste des cours
    public void execute() {
        // Créer un Executor pour exécuter la tâche sur un thread séparé
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    // URL pour l'api de connexion
                    URL url = new URL("http://10.0.2.2:8080/api/cours/search/all");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    // Obtenir le code d'état de la réponse
                    int statusCode = urlConnection.getResponseCode();

                    // Lire la réponse JSON depuis le flux d'entrée de la connexion
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        try (InputStream inputStream = urlConnection.getInputStream()) {
                            // Convertir la réponse JSON en liste d'objets Cours
                            List<Cours> coursList = parseJsonResponse(inputStream);

                            // Envoyer la liste des cours au thread principal via le Handler
                            Message message = Message.obtain();
                            message.obj = coursList;
                            handler.sendMessage(message);
                        }
                    } else {
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

    // Méthode pour convertir la réponse JSON en liste d'objets Cours
    private List<Cours> parseJsonResponse(InputStream inputStream) throws IOException, JSONException {
        JSONArray jsonResponse = new JSONArray(new Scanner(inputStream).useDelimiter("\\A").next());
        List<Cours> coursList = new ArrayList<>();
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject coursObject = jsonResponse.getJSONObject(i);
            int idCours = coursObject.getInt("id");
            String nomCours = coursObject.getString("nomCours");
            String horaire = coursObject.getString("horaire");
            String lieu = coursObject.getString("lieu");
            String description = coursObject.getString("description");
            String instructeur = coursObject.getString("instructeur");

            // Construire une classe Cours et l'ajouter à la liste pour les convertir
            Cours cours = new Cours(idCours, nomCours, horaire, lieu, description, instructeur);
            coursList.add(cours);
        }
        return coursList;
    }
}
