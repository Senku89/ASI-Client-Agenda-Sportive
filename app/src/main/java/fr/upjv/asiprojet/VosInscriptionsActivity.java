package fr.upjv.asiprojet;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VosInscriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vos_inscriptions);
        ListView ListVosInscriptions = findViewById(R.id.Liste2);
        List<String> List = new ArrayList<>();
        List.add("Tennis 12/01/2024");
        List.add("Football 13/02/2024");
        List.add("Rugby 14/02/2024");
        List.add("Basketball 22/02/2024");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,List);
        ListVosInscriptions.setAdapter(arrayAdapter);
    }


}