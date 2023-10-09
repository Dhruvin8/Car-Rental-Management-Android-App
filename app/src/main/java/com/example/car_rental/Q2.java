package com.example.car_rental;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Q2 extends AppCompatActivity {
    EditText VId;
    EditText VDes;
    EditText VType;
    EditText VCategory;
    String Vid, Vdes, Vtype, Vcategory ;
    Button q2;
    Repo repo;
    Button btnFetchVehicle;
    RecyclerView vehicleRecyclerView;
    VehicleAdapter adapter;
    ArrayList<VehicleModel> item = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q2);

        repo = new Repo(getApplicationContext());
        VId = findViewById(R.id.vehicleId);
        VDes = findViewById(R.id.vehicleDescription);
        VType = findViewById(R.id.vehicleType);
        VCategory = findViewById(R.id.vehicleCategory);
        q2 = findViewById(R.id.btnUpdateVehInfo);
        vehicleRecyclerView = findViewById(R.id.recyclerView_vehicle);
        btnFetchVehicle = findViewById(R.id.btnFetchVehicle);
        setRecyclerView();


        q2.setOnClickListener(view -> {
            Vid = VId.getText().toString();
            Vdes = VDes.getText().toString();
            Vtype = VType.getText().toString();
            Vcategory = VCategory.getText().toString();
            System.out.println("Vehicle ID is " + Vid + "Vehicle Description is " + Vdes+ "Vehicle Type is " + Vtype+ "Vehicle Description is " + Vcategory );

            VehicleModel vehicleModel = repo.insertVehicle(Vid, Vdes, Vtype,Vcategory);
            if (vehicleModel != null) {
                item.add(vehicleModel);
                adapter.notifyDataSetChanged();
            }


        });

        btnFetchVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = repo.readAllVehicles();
                adapter.notifyDataSetChanged();

            }
        });


    }


    private void setRecyclerView() {
        vehicleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        item = repo.readAllVehicles();
        adapter = new VehicleAdapter(item);
        vehicleRecyclerView.setAdapter(adapter);
    }
}