package csc1304.gr13.retailmanagercsc.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;


import csc1304.gr13.retailmanagercsc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public abstract class ListMenuFragment extends MenuFragment {
    private boolean hasShown = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.hasShown = savedInstanceState.getBoolean("hasShown");
        }
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, null);
        ListView listView = (ListView) view.findViewById(R.id.list_menu);
        final ArrayList arrayList = new ArrayList();
        final Map<Integer, Object> buttonMap = new HashMap<>();
        final ArrayList arrayList2 = arrayList;
        listView.setAdapter(new SimpleAdapter(getActivity(), arrayList, R.layout.list_menu_item, new String[]{"image", "text"}, new int[]{R.id.item_image, R.id.item_text}) {
            public View getView(int position, View convertView, ViewGroup parent) {
                RelativeLayout vg = (RelativeLayout) super.getView(position, convertView, parent);
                Object bmp = ((Map) arrayList2.get(position)).get("drawable");
                if (bmp == null) {
                    return super.getView(position, convertView, parent);
                }
                ((ImageView) vg.findViewById(R.id.item_image)).setImageDrawable((Drawable) bmp);
                return vg;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int itemIndex, long arg3) {
                ListMenuFragment.this.onButtonClicked(arrayList, buttonMap, itemIndex);
            }
        });
        setCurrentItems(arrayList, buttonMap);
        return view;
    }

    /* access modifiers changed from: protected */
    public boolean isFragmentShown() {
        return this.hasShown;
    }

    /* access modifiers changed from: protected */
    public Intent getStartIntent(Object controllerName, int buttonId) {
        return null;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasShown", true);
    }
}
