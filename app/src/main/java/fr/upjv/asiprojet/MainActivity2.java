package fr.upjv.asiprojet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
//deuxieme bouton
    public void PageSuivante(View view) {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
}