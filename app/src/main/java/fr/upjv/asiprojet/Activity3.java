package fr.upjv.asiprojet;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        ListView ListView = findViewById(R.id.Liste2);
        List<String> List = new ArrayList<>();
        List.add("Tennis");
        List.add("Football");
        List.add("Rugby");
        List.add("Basketball");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,List);
        ListView.setAdapter(arrayAdapter);

    }


    public void VosInscriptions(View view) {
        Intent intent = new Intent(this, VosInscriptionsActivity.class);
        startActivity(intent);
    }

    public void sinscrire(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }



}