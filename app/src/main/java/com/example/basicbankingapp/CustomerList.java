package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.basicbankingapp.Adapters.CustomersListAdapter;
import com.example.basicbankingapp.Handlers.DBHandler;
import com.example.basicbankingapp.Models.CustomerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomersListAdapter recyclerViewAdapter;
    private ArrayList<CustomerModel> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        recyclerView = findViewById(R.id.rvCustomerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        customers.clear();

        Cursor cursor = new DBHandler(this).showCustomers();
        while(cursor.moveToNext()){

            CustomerModel holder = new CustomerModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            this.customers.add(holder);

        }

        recyclerViewAdapter = new CustomersListAdapter(CustomerList.this, customers);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(CustomerList.this, TransactionList.class));

            }
        });

    }
}