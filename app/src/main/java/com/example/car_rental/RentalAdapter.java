package com.example.car_rental;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {

    private ArrayList<RentalInfoModel> model;

    private OnClickListener onClickListener;


    public RentalAdapter(ArrayList<RentalInfoModel> model, OnClickListener onClickListener) {
         this.model = model;
         this.onClickListener = onClickListener;
    }

    public RentalAdapter(ArrayList<RentalInfoModel> model) {
        this.model = model;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView phone;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.remBal);

        }

        public ViewHolder(View view, OnClickListener onClickListener) {
            super(view);

            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.remBal);

            itemView.setOnClickListener(view1 -> {
                onClickListener.onClick(getAdapterPosition());
            });

        }

        public TextView getId() {
            return id;
        }

        public TextView getName() {
            return name;
        }

        public TextView getPhone() {
            return phone;
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_vrentalinfo, viewGroup, false);

        if (onClickListener != null) {
            return new ViewHolder(view, onClickListener);
        }
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        RentalInfoModel m = model.get(position);
        viewHolder.getId().setText(m.getCustomerId());
        viewHolder.getName().setText(m.getName());
        String bal = m.getRemainingBalance();
        viewHolder.getPhone().setText(bal);






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return model.size();
    }
}

interface OnClickListener {
    void onClick(int position);
}
