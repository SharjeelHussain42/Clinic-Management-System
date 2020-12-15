package com.sharjeelhussain.smd_project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter {
    /**
     * Your Data list of String which have a list of entryies shown in View
     */
    private ArrayList<String> dataList;
    /**
     * Your Activity Context need for basic feature use
     */
    private Context mContext;
    /**
     * Your Single Item XML Layout Id will be in used for making Your Custom Array Adapter View
     */
    private int itemLayout;
    /**
     * Filter List for handling data changing, range changes and managing data globally
     */
    private ListFilter listFilter = new ListFilter();
    /**
     * Another array used to perform some in-module operations
     */
    private List<String> dataListAllItems;
    /**
     * @param context Context of your Activity
     * @param resource Single Item Layout Id
     * @param storeDataLst Data List
     */
    public CustomArrayAdapter(Context context, int resource, ArrayList<String> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
        mContext = context;
        itemLayout = resource;
    }
    /**
     * Handle your view counting or size based of list size
     * @return return total item size
     */
    @Override
    public int getCount() {
        return dataList.size();
    }
    /**
     * return item based on click position
     * @param position item position in view
     * @return item
     */
    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }
    /**
     * Used in filter operation
     * @return listFilter
     */
    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }
    /**
     * View customization perform here with your custom view and there elements
     * @param position Position of current item
     * @param view View object
     * @param parent ViewGroup
     * @return View Object
     */
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }
        view.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        TextView strName = view.findViewById(R.id.Name);
        TextView strPh = view.findViewById(R.id.Email);
        strName.setText(getItem(position).split("-")[0]);
        strPh.setText(getItem(position).split("-")[1]);
        return view;
    }
    public class ListFilter extends Filter {
        private Object lock = new Object();
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<>(dataList);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<String> matchValues = new ArrayList<String>();
                for (String dataItem : dataListAllItems) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<String>) results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
