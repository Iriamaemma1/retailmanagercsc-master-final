package csc1304.gr13.retailmanagercsc.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public abstract class MenuFragment extends Fragment {
    private Map<Integer, Object> currentButtonMap;
    private List<HashMap<String, Object>> currentItems;
    private boolean isTransactionStarted;
    private OnTransactionItemClickListener listener;

    public interface OnTransactionItemClickListener {
    }

    /* access modifiers changed from: protected */
    public abstract View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract Intent getStartIntent(Object obj, int i);

    /* access modifiers changed from: protected */
    public abstract void onNormalItemClick(String str);

    /* access modifiers changed from: protected */
    public abstract boolean onTransactionItemClick(Object obj);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void setCurrentItems(List<HashMap<String, Object>> items, Map<Integer, Object> map) {
        this.currentItems = items;
        this.currentButtonMap = map;
    }

    /* access modifiers changed from: protected */
    public void addNormalItem(String text, String iconPath) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "normal");
        map.put("drawable", Drawable.createFromPath(iconPath));
        map.put("text", text);
        this.currentItems.add(map);
    }

    /* access modifiers changed from: protected */
    public void addNormalItem(String text, int iconId) {
        addControlItem(text, iconId, "normal");
    }

    private Map<String, Object> addControlItem(String text, int iconId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("image", Integer.valueOf(iconId));
        map.put("text", text);
        if (!isControlItemExisted(map)) {
            this.currentItems.add(map);
        }
        return map;
    }

    /* access modifiers changed from: protected */
    public void addTransactionItem(Object transactionName, String text, int iconId) {
        if (!isTransactionItemExisted(transactionName)) {
            this.currentButtonMap.put(Integer.valueOf(this.currentItems.size()), transactionName);
            addNormalItem(text, iconId);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isTransactionItemExisted(Object transactionName) {
        if (this.currentButtonMap.containsValue(transactionName)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isControlItemExisted(HashMap<String, Object> newItem) {
        for (HashMap<String, Object> item : this.currentItems) {
            if (item.get("text").equals(newItem.get("text")) && item.get("image").equals(newItem.get("image"))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onButtonClicked(List<HashMap<String, Object>> items, Map<Integer, Object> buttonMap, int buttonId) {
        if (!this.isTransactionStarted) {
            Object transactionObject = buttonMap.get(Integer.valueOf(buttonId));
            if (transactionObject == null) {
                String itemType = (String) ((HashMap) items.get(buttonId)).get("type");
                if (itemType == null) {
                    //ToastUtil.toast("Not support this transaction currently! Button[" + buttonId + "]" + ((HashMap) items.get(buttonId)).get("text").toString(), 0);
                } else if ("normal".equals(itemType)) {
                    onNormalItemClick(((HashMap) items.get(buttonId)).get("text").toString());
                }
            } else {
                if (!onTransactionItemClick(transactionObject)) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public OnTransactionItemClickListener getListener() {
        return this.listener;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (OnTransactionItemClickListener) activity;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
