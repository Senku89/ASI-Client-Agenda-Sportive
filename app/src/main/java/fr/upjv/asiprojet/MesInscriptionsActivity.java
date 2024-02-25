package fr.upjv.asiprojet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MesInscriptionsActivity extends AppCompatActivity {
    private int idUser;
    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_inscriptions);

        // Obtenir l'id et le nom de de l'intent
        Intent intent = getIntent();
        this.idUser = intent.getIntExtra("idUser", -1);
        this.nom = intent.getStringExtra("nom");

        Log.d(TAG, "ID: " + idUser + ", Nom: " + nom); // Affichage des valeurs

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