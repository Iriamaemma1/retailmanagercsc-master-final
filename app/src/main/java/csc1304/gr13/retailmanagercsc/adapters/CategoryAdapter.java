package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;
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
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.modelClass.Thumbnail;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<CategoryModel> categories;
    private Thumbnail thumbnail;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(categories.get(position).getCategory_name());
        if(categories.get(position).getCategory_description().length()==0){
            holder.count.setText("StocK Category");
        }else {
            holder.count.setText(categories.get(position).getCategory_description());
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
        inflater.inflate(R.menu.menu_category, popup.getMenu());
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
                case R.id.action_edit_category:
                    Toast.makeText(context, "Under Implementaion", Toast.LENGTH_SHORT).show();

                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            count =  view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
        }
    }



    public CategoryAdapter(@NonNull Context context, @NonNull ArrayList<CategoryModel> objects) {
        this.context = context;
        categories = objects;
    }

    public void filterList(ArrayList<CategoryModel> filterdNames) {
        this.categories = filterdNames;
        notifyDataSetChanged();
    }

}
