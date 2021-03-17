package com.example.basicbankingapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicbankingapp.Models.CustomerModel;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.TransactionList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private ArrayList<CustomerModel> customers;
    private Context context;
    private TransactionList transactionList;

    public TransactionListAdapter(ArrayList<CustomerModel> customers, Context context, TransactionList transactionList) {
        this.customers = customers;
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_sample, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fromUser.setText(customers.get(position).getUserFrom());
        holder.toUser.setText(customers.get(position).getUserTo());
        holder.dateOfTransaction.setText(customers.get(position).getTransactionDate());
        holder.transactionID.setText("Transaction ID: #"+ customers.get(position).getTransactionId());

        if(customers.get(position).getTransactionStatus().equals("Failed")){
            holder.transactAmount.setText("₹"+ customers.get(position).getCustomerBal());
            holder.transactAmount.setTextColor(Color.GRAY);
            holder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_failed));
        }
        else if (customers.get(position).getTransactionStatus().equals("Add")){

            holder.transactAmount.setText("+ ₹"+ customers.get(position).getCustomerBal());
            holder.transactAmount.setTextColor(Color.parseColor("#4BB543"));
            holder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success));

        }
        else{

            holder.transactAmount.setText("- ₹"+ customers.get(position).getCustomerBal());
            holder.transactAmount.setTextColor(Color.parseColor("#f40404"));
            holder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success));

        }

    }

    @Override
    public int getItemCount() {

        return customers.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView statusImage;
        public TextView fromUser, toUser, dateOfTransaction, transactionID, transactAmount;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            statusImage = itemView.findViewById(R.id.statusImage);
            fromUser = itemView.findViewById(R.id.fromUser);
            toUser = itemView.findViewById(R.id.toUser);
            dateOfTransaction = itemView.findViewById(R.id.dateOfTransaction);
            transactionID = itemView.findViewById(R.id.transactionID);
            transactAmount = itemView.findViewById(R.id.transactAmount);

        }

    }

}


