package fr.upjv.asiprojet;

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
    private int id;
    private String nom;

    ListView listView;
    private static final String TAG = "ListCoursActivity"; // Definir TAG pour cette class
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            List<Cours> coursList = (List<Cours>) msg.obj;
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
        setContentView(R.layout.calendar_activity);

        this.listView = findViewById(R.id.Liste2);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.id = intent.getIntExtra("id", -1);
        this.nom = intent.getStringExtra("nom");

        Log.d(TAG, "ID: " + id + ", Nom: " + nom);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int position,long id){

                startActivity(new Intent(ListCoursActivity.this,InscriptionsActivity.class));
            }

        });
        // Creer et executer la tache pour la liste des cours
        new ListCoursTask(handler).execute();




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

    public void VosInscriptions(View view) {
        Intent intent = new Intent(this, VosInscriptionsActivity.class);
        startActivity(intent);
    }

    public void pagePrecedente(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("nom", nom);
        startActivity(intent);
    }
}