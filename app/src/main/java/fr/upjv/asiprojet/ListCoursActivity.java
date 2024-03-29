package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.upjv.asiprojet.models.Cours;
import fr.upjv.asiprojet.tasks.ListCoursTask;

public class ListCoursActivity extends AppCompatActivity {
    private int idUser;
    private String nom;
    private List<Cours> coursList;
    private ListView listView;

    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            coursList = (List<Cours>) msg.obj;
            if (coursList != null) {
                // Mettre à jour l'UI avec la liste des objets Cours
                listerListCours(coursList);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcours_activity);

        this.listView = findViewById(R.id.Liste2);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");

        Log.d(TAG, "ID: " + idUser + ", Nom: " + nom); // Affichage des valeurs

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int position,long id){

                Cours selectedCours = coursList.get(position); // Obtenir la position du cours

                // Valeurs a passer pour realiser les inscriptions
                Intent intent = new Intent(ListCoursActivity.this, InscriptionsActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("nom", nom);
                intent.putExtra("idCours", selectedCours.getIdCours());
                intent.putExtra("Cours", selectedCours.toStringDetaille());
                startActivity(intent);
            }
        });
        // Creer et executer la tache pour la liste des cours
        new ListCoursTask(handler).execute();
    }

    private void listerListCours(List<Cours> coursList) {
        // Créer un adaptateur personnalisé pour afficher les objets Cours
        ArrayAdapter<Cours> arrayAdapter = new ArrayAdapter<Cours>(this, android.R.layout.simple_list_item_1, coursList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Vérifier si la vue est réutilisée, sinon gonfler la vue
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                // Obtenir l'objet Cours actuel
                Cours cours = getItem(position);

                // Définir le texte du TextView selon la représentation de chaîne personnalisée
                if (cours != null) {
                    TextView textView = convertView.findViewById(android.R.id.text1);
                    textView.setText(cours.toStringSansDetailles());
                }

                return convertView;
            }
        };

        // Définir l'adaptateur sur le ListView
        listView.setAdapter(arrayAdapter);
    }

    public void mesInscriptions(View view) {
        Intent intent = new Intent(this, MesInscriptionsActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }

    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}