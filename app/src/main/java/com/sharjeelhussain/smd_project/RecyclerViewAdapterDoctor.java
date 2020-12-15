package com.sharjeelhussain.smd_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterDoctor extends RecyclerView.Adapter<RecyclerViewAdapterDoctor.MyViewHolder> {

    private List<AppointmentModel> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout row;
        public TextView Name;
        public TextView Email;
        public TextView Phone;

        Context ctx;

        public MyViewHolder(View v, Context ct) {
            super(v);
            Name = v.findViewById(R.id.name);
            Email = v.findViewById(R.id.email);
            Phone = v.findViewById(R.id.phone);
            row=v.findViewById(R.id.patientcard);
            ctx = ct;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapterDoctor(List<AppointmentModel> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapterDoctor.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_card, parent, false);
        RecyclerViewAdapterDoctor.MyViewHolder vh = new RecyclerViewAdapterDoctor.MyViewHolder(v,parent.getContext());
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerViewAdapterDoctor.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.Name.setText(mDataset.get(position).getPtName());
        holder.Email.setText(mDataset.get(position).getPtEmail());
        holder.Phone.setText(mDataset.get(position).getPhone());


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent display=new Intent(holder.ctx, Prescription.class);
                display.putExtra("PtName",mDataset.get(position).getPtName());
                display.putExtra("DrEmail",mDataset.get(position).getDrEmail());
                display.putExtra("PtEmail",mDataset.get(position).getPtEmail());
                display.putExtra("Time",mDataset.get(position).getTiming());
                display.putExtra("Date",mDataset.get(position).getDate());
                display.putExtra("Problem",mDataset.get(position).getProblem());
                holder.ctx.startActivity(display);
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
