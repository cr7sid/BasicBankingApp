package com.example.basicbankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicbankingapp.Handlers.DBHandler;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CustomerDetails extends AppCompatActivity {

    private TextView tvName, tvEmail, tvImage, tvAccNum, tvBankName, tvIFSC, tvBal;
    private Button btnAddMoney, btnSelectUser;
    private EditText etMoney;
    private int customerId;
    private Double customerBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        tvName = findViewById(R.id.tvCustomerName);
        tvEmail = findViewById(R.id.tvCustomerEmail);
        tvImage = findViewById(R.id.tvCustomerImage);
        tvAccNum = findViewById(R.id.tvAccNum);
        tvBankName = findViewById(R.id.tvBankName);
        tvIFSC = findViewById(R.id.tvIfscCode);
        tvBal = findViewById(R.id.tvAccBal);
        etMoney = findViewById(R.id.etSendMoney);

        btnAddMoney = findViewById(R.id.btnAddMoney);
        btnSelectUser = findViewById(R.id.btnSelectUser);

        customerId = getIntent().getIntExtra("customerId", 0);
        Log.i("ID",String.valueOf(customerId));
        loadCustomerData(customerId);

        btnAddMoney.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addMoney();

            }
        });

        btnSelectUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sendMoney();

            }
        });

    }

    public void addMoney() {

        if(etMoney.getText().toString().isEmpty()) {

            etMoney.setError("This field can't be empty!");

        } else {

            Double currentBalance = customerBal;
            Double addAmount = Double.parseDouble(etMoney.getText().toString());
            Double totalBalance = currentBalance + addAmount;

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");

            long transactionID = new Random().nextInt((9999999 - 1000000) + 1) + 1000000;
            new DBHandler(CustomerDetails.this).updateBal(customerId, totalBalance);
            new DBHandler(CustomerDetails.this).transactionStatement(
                    simpleDateFormat.format(calendar.getTime()),
                    tvName.getText().toString(), "To Myself",
                    totalBalance.toString(), "Added", transactionID);

            startActivity(getIntent());
            finish();
            Toast.makeText(CustomerDetails.this,"₹"+ addAmount +" successfully added to your account.", Toast.LENGTH_LONG).show();

        }

    }

    public void sendMoney() {

        if(etMoney.getText().toString().isEmpty()) {

            etMoney.setError("This field can't be empty!");

        } else if(Double.parseDouble(etMoney.getText().toString()) > customerBal ) {

            etMoney.setError("Insufficient Funds!");

        } else {

            Intent intent = new Intent(CustomerDetails.this, TransactToActivity.class);
            intent.putExtra("id", String.valueOf(customerId));
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("balance", customerBal.toString());
            intent.putExtra("amount", etMoney.getText().toString());
            startActivity(intent);
            finish();

        }

    }

    public void loadCustomerData(int id) {

        Cursor cursor = new DBHandler(this).showCustomerData(id);

        while (cursor.moveToNext()) {

            tvName.setText(cursor.getString(1));
            tvImage.setText(cursor.getString(1).charAt(0) + "");
            tvEmail.setText(cursor.getString(2));
            tvAccNum.setText("Account Number: " + cursor.getString(3));
            tvBankName.setText("Bank: " + cursor.getString(4));
            tvIFSC.setText("IFSC Code: " + cursor.getString(5));
            customerBal = Double.parseDouble(cursor.getString(6));

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            tvBal.setText("Account Balance: ₹"+ nf.format(customerBal));

        }

    }

}