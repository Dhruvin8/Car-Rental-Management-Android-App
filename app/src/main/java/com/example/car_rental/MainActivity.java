package com.example.car_rental;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Button b1, b2, b3, b4, q5a, q5b;
    Toolbar toolbar;
    TextView header;

    private ConnectingDatabase cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.custom_toolbar);
        header = findViewById(R.id.header_text);

        header.setText("Home Page");

        b1= (Button) findViewById(R.id.button1);
        b2= (Button) findViewById(R.id.button2);
        b3= (Button) findViewById(R.id.button3);
        b4= (Button) findViewById(R.id.button4);
        q5a = findViewById(R.id.q5a);
        q5b = findViewById(R.id.q5b);

        b1.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q1.class);
            startActivity(intent);
        });

        b2.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q2.class);
            startActivity(intent);
        });

        b3.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q3.class);
            startActivity(intent);
        });

        b4.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q4.class);
            startActivity(intent);
        });
        q5a.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q5a.class);
            startActivity(intent);
        });

        q5b.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,Q5b.class);
            startActivity(intent);
        });

    }
}