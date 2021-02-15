package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.Thumbnail;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<CustomerModel> customers;


    private Thumbnail thumbnail;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.customerName.setText(customers.get(position).getCustomerName());
        holder.customerGender.setText(customers.get(position).getCustomerGender());
        holder.customerContact.setText(customers.get(position).getCustomerPhone());
        holder.customerType.setText(customers.get(position).getCustomerType());

        thumbnail = new Thumbnail(R.drawable.user);
        // loading album cover using Glide library
        Glide.with(context).load(thumbnail.getThumbnail()).into(holder.thumbnail);


    }


    @Override
    public int getItemCount() {
        return customers.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName, customerGender,customerContact,customerType;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            customerName = view.findViewById(R.id.cust_name_textview);
            customerGender =  view.findViewById(R.id.gender_textview);
            customerContact =  view.findViewById(R.id.contact_textview);
            customerContact =  view.findViewById(R.id.contact_textview);
            customerType = view.findViewById(R.id.customer_type_textview);
            thumbnail = view.findViewById(R.id.thumbnail);

        }
    }


    public CustomerAdapter(@NonNull Context context, @NonNull ArrayList<CustomerModel> objects) {
        this.context = context;
        customers = objects;
    }



    public void filterList(ArrayList<CustomerModel> filterdNames) {
        this.customers = filterdNames;
        notifyDataSetChanged();
    }
}
