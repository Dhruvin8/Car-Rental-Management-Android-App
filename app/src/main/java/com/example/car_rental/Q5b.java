package com.example.car_rental;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Q5b extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText tvSearchVin, tvSearchDesc;
    Button searchBtn;
    RentalAdapter adapter;
    ArrayList<RentalInfoModel> items = new ArrayList<>();
    Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q5b);


        tvSearchVin = findViewById(R.id.searchB_vin);
        tvSearchDesc = findViewById(R.id.searchB_vehDescription);
        recyclerView = findViewById(R.id.recyclerViewB);
        searchBtn = findViewById(R.id.buttonSearchB);
        repo =  new Repo(getApplicationContext());
        searchBtn.setOnClickListener(
                view -> {
                    String vin = tvSearchVin.getText().toString();
                    String desc = tvSearchDesc.getText().toString();
                    ArrayList<RentalInfoModel> model = repo.searchQuery5b(vin, desc);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new RentalAdapter(model);
                    recyclerView.setAdapter(adapter);
                }
        );

    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RentalAdapter(items);
        recyclerView.setAdapter(adapter);
    }


}