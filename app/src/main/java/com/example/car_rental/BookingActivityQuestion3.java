package com.example.car_rental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivityQuestion3 extends AppCompatActivity {



    private String startDate, endDate, returnDate, vId,  custId, qty, rentalType;
    private TextView etCustId, etRentalQty, etRentalType, etReturnDate;
    private Button btnPayNow;
    private Button btnPayLater;

    private Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_question3);
        getDetailsFromPreviousPage();

        etCustId = findViewById(R.id.etCustomerId);
        btnPayLater = findViewById(R.id.btnPayLater);
        btnPayNow = findViewById(R.id.btnPayNow);
        etRentalQty = findViewById(R.id.etRentalQty);
        etRentalType = findViewById(R.id.etRentalType);
        etReturnDate = findViewById(R.id.erReturnDate);

        repo = new Repo(getApplicationContext());

        payNow();
        payLater();

    }

    private void getDetailsFromPreviousPage() {
        Intent intent = getIntent();
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        vId = intent.getStringExtra("vId");

    }

    private void  payNow() {
        btnPayNow.setOnClickListener(view -> {
            custId = etCustId.getText().toString();
            rentalType = etRentalType.getText().toString();
            qty = etRentalQty.getText().toString();
            returnDate = etReturnDate.getText().toString();
            if(custId.isEmpty()) {
                Toast.makeText(this, "Please enter customer id", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    repo.searchQuery3b(custId, vId, startDate, endDate, rentalType, qty, returnDate, true);
                    Toast.makeText(this, "Book Successful For Pay Now", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });
    }

    private void  payLater() {
        btnPayLater.setOnClickListener(view -> {
            custId = etCustId.getText().toString();
            rentalType = etRentalType.getText().toString();
            qty = etRentalQty.getText().toString();
            returnDate = etReturnDate.getText().toString();

            if(custId.isEmpty()) {
                Toast.makeText(this, "Book Successful For Pay Later", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    repo.searchQuery3b(custId, vId, startDate, endDate, rentalType, qty, returnDate,false);

                } catch (Exception e) {

                }

            }
        });
    }

}