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
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.Thumbnail;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<SupplierModel> suppliers;

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
        holder.customerName.setText(suppliers.get(position).getSupplier_company_name());
        holder.customerGender.setText(suppliers.get(position).getSupplier_name());
        holder.customerContact.setText(suppliers.get(position).getSupplier_phone_number());
        holder.customerType.setText(suppliers.get(position).getSupplier_address());

        thumbnail = new Thumbnail(R.drawable.supplier);
        // loading album cover using Glide library
        Glide.with(context).load(thumbnail.getThumbnail()).into(holder.thumbnail);


    }


    @Override
    public int getItemCount() {
        return suppliers.size();
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


    public SupplierAdapter(@NonNull Context context, @NonNull ArrayList<SupplierModel> objects) {
        this.context = context;
        suppliers = objects;
    }



    public void filterList(ArrayList<SupplierModel> filterdNames) {
        this.suppliers = filterdNames;
        notifyDataSetChanged();
    }
}
