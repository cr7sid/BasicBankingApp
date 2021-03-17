package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.basicbankingapp.Adapters.TransactionListAdapter;
import com.example.basicbankingapp.Handlers.DBHandler;
import com.example.basicbankingapp.Models.CustomerModel;

import java.text.NumberFormat;
import java.util.ArrayList;

public class TransactionList extends AppCompatActivity {

    private ArrayList<CustomerModel> customers = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionListAdapter adapter;
    private TextView noTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        recyclerView = findViewById(R.id.rvTransactList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        noTransactions = findViewById(R.id.noTransaction);

        findViewById(R.id.fabInfo).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(TransactionList.this, CustomerList.class));

            }
        });

        showData();

    }

    public void showData() {

        customers.clear();
        Cursor cursor = new DBHandler(this).getTransactions();

        while (cursor.moveToNext()) {
            String amountFromDB = cursor.getString(4);
            Double amt = Double.parseDouble(amountFromDB);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String amount = nf.format(amt);

            CustomerModel customer = new CustomerModel(cursor.getString(2),
                    cursor.getString(3), amount, cursor.getString(5), cursor.getString(6),
                    cursor.getString(1));
            customers.add(customer);
        }

        adapter = new TransactionListAdapter(customers, getApplicationContext(), TransactionList.this);
        recyclerView.setAdapter(adapter);

        if(customers.size() == 0){

            noTransactions.setVisibility(View.VISIBLE);

        }

    }

}