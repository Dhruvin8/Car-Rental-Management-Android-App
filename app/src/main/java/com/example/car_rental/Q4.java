package com.example.car_rental;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class Q4 extends AppCompatActivity {

    Toolbar toolbar;
    TextView header;

    private TextView tvRemBal;
    private Button btnPay, btnSearch;

    private EditText etVehId;
    private EditText etCustId;
    private EditText etDate;

    private Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q4);

        repo = new Repo(getApplicationContext());

        toolbar = findViewById(R.id.custom_toolbar);
        header = findViewById(R.id.header_text);
        header.setText("Query4");

        etVehId = findViewById(R.id.vehId);
        etCustId = findViewById(R.id.custId);
        etDate = findViewById(R.id.date);
        btnPay = findViewById(R.id.btnPayBal);
        btnSearch = findViewById(R.id.btnSearchBal);
        tvRemBal = findViewById(R.id.remBal);

        search();
        payRental();

    }

    private void search() {
        btnSearch.setOnClickListener(
                view -> {
                    String custId = etCustId.getText().toString();
                    String vehId = etVehId.getText().toString();
                    String date = etDate.getText().toString();
                    String remBal = repo.searchQuery4(date, vehId, custId);
                    remBal = "$ "+remBal;
                    tvRemBal.setText(remBal);
                }
        );
    }


    private void payRental() {
        btnPay.setOnClickListener(view -> {
            String custId = etCustId.getText().toString();
            String vehId = etVehId.getText().toString();
            String date = etDate.getText().toString();
            String bal = repo.updateRental(date, vehId, custId);
            Toast.makeText(this, "Rental Paid", Toast.LENGTH_SHORT).show();
        });
    }


}