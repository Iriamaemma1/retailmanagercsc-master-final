package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;
import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.Thumbnail;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<PurchaseModel> purchases;
    private Product product;
    private ProductListModel a_product;

    private Supplier supplier;
    private ArrayList<SupplierModel> a_supplier;

    private Thumbnail thumbnail;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        product = new Product(context);
        supplier = new Supplier(context);
        a_product = product.getProductDetails(""+purchases.get(position).getProduct_id());

        a_supplier = supplier.getSupplier(""+purchases.get(position).getSupplier_id());

        //PurchaseModel pModel = new PurchaseModel();
        //pModel.setProduct_name(a_product.getpName());
        //pModel.setSupplier_name(a_supplier.get(0).getSupplier_name());
       // purchases.set(position,pModel);

        if(a_product==null){
            holder.title.setText("...");
        }else{
            holder.title.setText(a_product.getpName());
        }

        String purchase_qty = purchases.get(position).getPurchase_product_quantity();
        String unit_purchase_price = purchases.get(position).getPurchase_amount();
        String purchase_date = purchases.get(position).getPurchase_date();
        Log.e("purchase_qty",purchase_qty);
        Log.e("unit_purchase_price",unit_purchase_price);

        holder.purchase_qty.setText( ""+purchase_qty+" Units");
        holder.unit_price.setText(""+unit_purchase_price+" UGX");
        holder.purchase_date.setText(""+purchase_date);


        thumbnail = new Thumbnail(R.drawable.default_product_img);
        // loading album cover using Glide library
        Glide.with(context).load(thumbnail.getThumbnail()).into(holder.thumbnail);

        /*holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showPopupMenu(holder.overflow);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, purchase_qty,unit_price,purchase_date;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            purchase_qty =  view.findViewById(R.id.purchase_qty);
            unit_price =  view.findViewById(R.id.unit_price);
            purchase_date = view.findViewById(R.id.purchase_date);
            thumbnail = view.findViewById(R.id.thumbnail);
           // overflow = view.findViewById(R.id.overflow);
        }
    }

    public PurchaseAdapter(@NonNull Context context, @NonNull ArrayList<PurchaseModel> objects) {
        this.context = context;
        purchases = objects;
    }

    public void filterList(ArrayList<PurchaseModel> filterdNames) {
        this.purchases = filterdNames;
        notifyDataSetChanged();
    }

}
