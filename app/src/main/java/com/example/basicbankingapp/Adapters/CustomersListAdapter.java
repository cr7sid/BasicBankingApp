package com.example.basicbankingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.basicbankingapp.CustomerDetails;
import com.example.basicbankingapp.Models.CustomerModel;
import com.example.basicbankingapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<CustomerModel> customers;

    public CustomersListAdapter(Context context, ArrayList<CustomerModel> customers) {

        this.context = context;
        this.customers = customers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sample, parent, false);
        return new ViewHolder(view, context);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.customerName.setText(customers.get(position).getCustomerName());
        holder.customerImage.setText(customers.get(position).getCustomerName().charAt(0) + "");
        holder.customerEmail.setText(customers.get(position).getCustomerEmail());

    }

    @Override
    public int getItemCount() {

        return customers.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView customerImage, customerName, customerEmail;

        public ViewHolder(@NonNull View itemView, Context ctx) {

            super(itemView);
            context = ctx;
            customerName = itemView.findViewById(R.id.customerName);
            customerEmail = itemView.findViewById(R.id.customerEmail);
            customerImage = itemView.findViewById(R.id.customerImage);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    CustomerModel user = customers.get(position);
                    Intent intent = new Intent(context, CustomerDetails.class);
                    Log.i("ID!!",String.valueOf(user.getId()));
                    intent.putExtra("customerId",user.getId());
                    context.startActivity(intent);

                }
            });

        }
    }

}
