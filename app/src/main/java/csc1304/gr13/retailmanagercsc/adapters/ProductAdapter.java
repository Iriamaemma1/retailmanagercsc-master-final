package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.Thumbnail;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.frgs.AddStockActivity;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.frgs.RemoveStockActivity;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<ProductDatabaseModel> product;


    private Thumbnail thumbnail;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(product.get(position).getProductName()+"("+product.get(position).getProductBrand()+")");
        String sell_price = product.get(position).getProductSellPrice();
        String product_price = product.get(position).getProductPrice();
        Log.e("Sell-Price",sell_price);
        Log.e("Product-Price",product_price);

        if(sell_price != null && sell_price.length() != 0 && !sell_price.equalsIgnoreCase(product_price)){
            holder.product_price.setText( ""+product_price+" UGX");
            holder.product_price.setPaintFlags(holder.product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sell_price.setText(""+sell_price+" UGX");
        }else{
            holder.product_price.setText( ""+product_price+" UGX");
        }


        thumbnail = new Thumbnail(R.drawable.default_product_img);
        // loading album cover using Glide library
        Glide.with(context).load(thumbnail.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_stock, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_stock:
                    Log.e("1",getItemCount()+"");

                    //Toast.makeText(context, "Add Stock", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, AddStockActivity.class);
                    context.startActivity(i);

                    return true;
                case R.id.action_disable_item:
                    Toast.makeText(context, "Disable Item,Still under Implementation", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_remove_stock:
                    //Toast.makeText(context, "Remove Stock", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(context, RemoveStockActivity.class);
                    context.startActivity(j);
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return product.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, product_price,sell_price;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            product_price =  view.findViewById(R.id.product_price);
            sell_price =  view.findViewById(R.id.sell_price);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
        }
    }


    public ProductAdapter(@NonNull Context context, @NonNull ArrayList<ProductDatabaseModel> objects) {
        this.context = context;
        product = objects;
    }


    /*
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_stock_lv,parent,false);

            holder.pName = (TextView) convertView.findViewById(R.id.stockPNameTv);
            holder.brandName = (TextView) convertView.findViewById(R.id.stockPBrandTv);
            holder.pCode = (TextView) convertView.findViewById(R.id.stockPCodeTv);
            holder.pSize = (TextView) convertView.findViewById(R.id.stockPSizeTv);
            holder.stockLimit = (TextView) convertView.findViewById(R.id.stockLimitTv);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.pName.setText(stocks.get(position).getpName());
        holder.brandName.setText(stocks.get(position).getBrandName());
        holder.pCode.setText(stocks.get(position).getpCode());
        holder.pSize.setText(stocks.get(position).getpSize());
        holder.stockLimit.setText(stocks.get(position).getStockLimit());

        return convertView;
    }
    */

    public void filterList(ArrayList<ProductDatabaseModel> filterdNames) {
        this.product = filterdNames;
        notifyDataSetChanged();
    }
}
