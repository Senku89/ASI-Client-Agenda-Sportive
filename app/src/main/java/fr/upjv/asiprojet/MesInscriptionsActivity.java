package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
import android.os.Bundle;
import fr.upjv.asiprojet.models.Cours;
import fr.upjv.asiprojet.tasks.ListCoursTask;
import fr.upjv.asiprojet.tasks.MesInscriptionsTask;

import java.util.List;

public class MesInscriptionsActivity extends AppCompatActivity {
    private int idUser;
    private String nom;
    private ListView listView;
    private List<Cours> coursList;

    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            coursList = (List<Cours>) msg.obj;
            if (coursList != null) {
                // Mettre à jour l'UI avec la liste des objets Cours
                updateUIWithCoursList(coursList);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_inscriptions);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");
        this.listView = findViewById(R.id.Liste2);

        Log.d(TAG, "ID: " + idUser + ", Nom: " + nom); // Affichage des valeurs

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int position,long id){

                Cours selectedCours = coursList.get(position); // Obtenir la position du cours

                // Pour effacer les inscriptions
                Intent intent = new Intent(MesInscriptionsActivity.this, DesinscriptionsActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("nom", nom);
                intent.putExtra("idCours", selectedCours.getIdCours());
                intent.putExtra("Cours", selectedCours.toStringDetaille());
                startActivity(intent);
            }
        });
        // Creer et executer la tache pour la liste des cours
        new MesInscriptionsTask(handler).execute(idUser);
    }

    private void updateUIWithCoursList(List<Cours> coursList) {
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

    public void pagePrecdente(View view) {
        Intent intent = new Intent(this, ListCoursActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }


}

/*
ListView ListVosInscriptions = findViewById(R.id.Liste2);
        List<String> List = new ArrayList<>();
        List.add("Tennis 12/01/2024");
        List.add("Football 13/02/2024");
        List.add("Rugby 14/02/2024");
        List.add("Basketball 22/02/2024");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,List);
        ListVosInscriptions.setAdapter(arrayAdapter);
        ListVosInscriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                startActivity(new Intent(VosInscriptionsActivity.this,DesinscriptionsActivity.class));
            }

        });
 */