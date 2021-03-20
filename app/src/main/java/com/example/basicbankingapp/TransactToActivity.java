package com.example.basicbankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.basicbankingapp.Adapters.TransactToAdapter;
import com.example.basicbankingapp.Handlers.DBHandler;
import com.example.basicbankingapp.Models.CustomerModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class TransactToActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactToAdapter recyclerViewAdapter;
    private ArrayList<CustomerModel> customers = new ArrayList<>();

    private String fromID, customerFrom, currentBalance, netBalance, transactAmount;
    private String toID, toCustomerName, selectedBalance, dateOfTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact_to);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        recyclerView = findViewById(R.id.rvTransacTo);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        dateOfTransaction = simpleDateFormat.format(calendar.getTime());

        Intent intent = getIntent();
        fromID = intent.getStringExtra("id");
        customerFrom = intent.getStringExtra("name");
        currentBalance = intent.getStringExtra("balance");
        transactAmount = intent.getStringExtra("amount");

        showData(fromID);

    }

    private void showData(String id) {

        customers.clear();
        Cursor cursor = new DBHandler(this).removeHolder(id);
        while(cursor.moveToNext()){

            Double balance = Double.parseDouble(cursor.getString(6));

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            CustomerModel customer = new CustomerModel(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),nf.format(balance));
            customers.add(customer);
        }

        recyclerViewAdapter = new TransactToAdapter(TransactToActivity.this, customers);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public void selectHolder(int position) {

        toID = String.valueOf(customers.get(position).getId());
        Cursor cursor = new DBHandler(this).transactToData(toID);

        while(cursor.moveToNext()) {

            toCustomerName = cursor.getString(1);
            selectedBalance = cursor.getString(6);
            Double currentBalance = Double.parseDouble(selectedBalance);
            Double transactAmount = Double.parseDouble(this.transactAmount);
            Double netBalance = currentBalance + transactAmount;

            long randomNum = new Random().nextInt((9999999 - 1000000) + 1) + 1000000;

            new DBHandler(this).transactionStatement(dateOfTransaction,
                    customerFrom, toCustomerName, this.transactAmount, "Success", randomNum);

            /*Log.d("ABC",this.transactAmount);
            Log.d("randomNum",String.valueOf(randomNum));*/

            new DBHandler(this).updateBal(Integer.parseInt(toID), netBalance);
            calculateNetBalance();
            startActivity(new Intent(TransactToActivity.this, TransactionSuccess.class));
            finish();

        }
    }

    private void calculateNetBalance() {

        Double currentBalance = Double.parseDouble(this.currentBalance);
        Double transactAmount = Double.parseDouble(this.transactAmount);
        Double netBalance = currentBalance - transactAmount;
        this.netBalance = netBalance.toString();
        new DBHandler(this).updateBal(Integer.parseInt(fromID), Double.parseDouble(this.netBalance));
    }

    @Override
    public void onBackPressed() {

        Random rn = new Random();
        long randomNum = rn.nextInt((9999999 - 1000000) + 1) + 1000000;

        AlertDialog.Builder closeButton = new AlertDialog.Builder(TransactToActivity.this);
        closeButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new DBHandler(TransactToActivity.this).transactionStatement(dateOfTransaction,
                                customerFrom, "None", transactAmount, "Failed",randomNum);
                        Toast.makeText(TransactToActivity.this, "Transaction Failed", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(TransactToActivity.this, TransactionList.class));
                        finish();

                    }
                }).setNegativeButton("NO", null);
        AlertDialog exit = closeButton.create();
        exit.show();
    }

}