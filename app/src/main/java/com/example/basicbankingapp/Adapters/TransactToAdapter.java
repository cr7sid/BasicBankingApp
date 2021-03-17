package com.example.basicbankingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.basicbankingapp.Models.CustomerModel;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.TransactToActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactToAdapter extends RecyclerView.Adapter<TransactToAdapter.ViewHolder> {

    private TransactToActivity transact;
    private ArrayList<CustomerModel> customers;

    public TransactToAdapter(TransactToActivity transact, ArrayList<CustomerModel> customers) {

        this.transact = transact;
        this.customers = customers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transact_to_sample, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.customerEmail.setText(customers.get(position).getCustomerEmail());
        holder.customerImage.setText(customers.get(position).getCustomerName().charAt(0) + "");
        holder.customerName.setText(customers.get(position).getCustomerName());
        holder.customerBalance.setText("₹" + customers.get(position).getCustomerBal());

    }

    @Override
    public int getItemCount() {

        return customers.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView customerImage, customerName, customerEmail, customerBalance;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            customerImage = itemView.findViewById(R.id.customerImage);
            customerName = itemView.findViewById(R.id.customerName);
            customerEmail = itemView.findViewById(R.id.customerEmail);
            customerBalance = itemView.findViewById(R.id.customerBalance);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    transact.selectHolder(getAdapterPosition());

                }
            });

        }
    }

}
