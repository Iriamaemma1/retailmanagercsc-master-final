package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationHolderClass;
import csc1304.gr13.retailmanagercsc.utils.HoldStaticVariationData;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class ProductVariationAdapter extends ArrayAdapter<ProductVariationHolderClass> {
    private Context mContext;
    private ArrayList<ProductVariationHolderClass> listState;
    private ProductVariationAdapter myAdapter;
    private boolean isFromView = false;


    public ProductVariationAdapter(Context context, int resource, List<ProductVariationHolderClass> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<ProductVariationHolderClass>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ((position == 0 || position==1)) {
            holder.mTextView.setText(listState.get(position).getTitle());
        }else{
            holder.mTextView.setText(listState.get(position).getTitle().split(",")[1]);
        }


        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0 || position==1)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);

        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    ProductVariationHolderClass selectedItems = new ProductVariationHolderClass();
                    if (isChecked) {
                        Log.e("CheckedItem", listState.get(position).getTitle().split(",")[0]+"="+listState.get(position).getTitle().split(",")[1]);
                        selectedItems.setTitle(listState.get(position).getTitle().split(",")[0]);
                        HoldStaticVariationData.chekedValue(position,selectedItems,isChecked);

                    } else {
                        Log.e("UnCheckedItem", listState.get(position).getTitle().split(",")[1]);
                        ProductVariationHolderClass unSelectedItems = new ProductVariationHolderClass();
                        HoldStaticVariationData.chekedValue(position,unSelectedItems,isChecked);
                    }

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}