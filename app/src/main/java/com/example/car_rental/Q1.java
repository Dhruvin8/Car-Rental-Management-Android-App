package com.example.car_rental;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Q1 extends AppCompatActivity {

    Toolbar toolbar;
    TextView header;

    EditText name, phone;
    String Name, Phone;
    Button q1;
    Repo repo;
    Button btnFetchCust;
    RecyclerView customerRecyclerView;
    CustomerAdapter adapter;
    ArrayList<CustomerModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q1);

        Intent intent = getIntent();
        repo = new Repo(getApplicationContext());
        toolbar = findViewById(R.id.custom_toolbar);
        header = findViewById(R.id.header_text);
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextPhone);
        q1 = findViewById(R.id.q1button);
        customerRecyclerView = findViewById(R.id.recyclerView_customer);
        btnFetchCust = findViewById(R.id.btnFetchCust);
        items = repo.readAllCustomers();
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView(items);


        q1.setOnClickListener(view -> {
            Name = name.getText().toString();
            Phone = phone.getText().toString();
            System.out.println("Name is " + Name + " Phone is " + Phone);
            System.out.println("inserting name and phone" + Name + Phone);
            CustomerModel customerModels = repo.insertCustomer(Name, Phone);
            if (customerModels != null) {
                items.add(customerModels);
                adapter.notifyDataSetChanged();
            }


        });

        btnFetchCust.setOnClickListener(view -> {
            items.clear();
            items = repo.readAllCustomers();
            setRecyclerView(items);
            adapter.notifyDataSetChanged();

        });

        header.setText("Query1");
    }


    private void setRecyclerView(ArrayList<CustomerModel> items) {
        adapter = new CustomerAdapter(items);
        customerRecyclerView.setAdapter(adapter);
    }
}