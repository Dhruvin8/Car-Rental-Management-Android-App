package com.example.car_rental;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Q5a extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText tvSearch;
    Button searchBtn;
    RentalAdapter adapter;
    ArrayList<RentalInfoModel> items = new ArrayList<>();
    Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q5a);


        tvSearch = findViewById(R.id.searchA);
        recyclerView = findViewById(R.id.recyclerViewA);
        searchBtn = findViewById(R.id.buttonSearchA);
        repo =  new Repo(getApplicationContext());
        searchBtn.setOnClickListener(
                view -> {

                    String query = tvSearch.getText().toString();
//                    if (!query.isEmpty()) {
                        ArrayList<RentalInfoModel> model = repo.searchQuery(query);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new RentalAdapter(model);
                        recyclerView.setAdapter(adapter);

//                    }
                }
        );

    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RentalAdapter(items);
        recyclerView.setAdapter(adapter);
    }


}