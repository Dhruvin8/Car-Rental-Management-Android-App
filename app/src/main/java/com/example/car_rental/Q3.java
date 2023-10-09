package com.example.car_rental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;

public class Q3 extends AppCompatActivity  implements  OnClickListener {

    Toolbar toolbar;
    TextView header;

    private RecyclerView recyclerView;
    private EditText etVehType, etCategory, etStartDate, etEndDate;
    private Button btnSearch;
    RentalAdapter adapter;
    ArrayList<RentalInfoModel> items = new ArrayList<>();

    private Repo repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q3);
        toolbar = findViewById(R.id.custom_toolbar);
        header = findViewById(R.id.header_text);
        header.setText("Query3");
        recyclerView = findViewById(R.id.recyclerView3);
        etVehType = findViewById(R.id.vehType);
        etCategory = findViewById(R.id.category);
        etStartDate = findViewById(R.id.startDate);
        etEndDate = findViewById(R.id.endDate);
        btnSearch = findViewById(R.id.btnSubmit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        repo = new Repo(getApplicationContext());

        search();
    }

    private void search() {
        btnSearch.setOnClickListener(view -> {
            String startDate = etStartDate.getText().toString();
            String endDate = etEndDate.getText().toString();
            String vType = etVehType.getText().toString();
            String category = etCategory.getText().toString();
            try {
                ArrayList<RentalInfoModel> model = repo.searchQuery3(vType, category, startDate, endDate);
                items = model;
                adapter = new RentalAdapter(model, this);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public void onClick(int position) {
        System.out.println("Item clicked position " + position);
        String id = items.get(position).getCustomerId();
        Intent intent= new Intent(this, BookingActivityQuestion3.class);
        String startDate = etStartDate.getText().toString();
        String endDate = etStartDate.getText().toString();
        String vType = etStartDate.getText().toString();
        String category = etStartDate.getText().toString();
        intent.putExtra("startDate", startDate);
        intent.putExtra("endDate", endDate);
        intent.putExtra("vType", vType);
        intent.putExtra("category", category);

        intent.putExtra("vId", id.substring(id.lastIndexOf("VIN: ") + 4).trim());
        startActivity(intent);
    }
}