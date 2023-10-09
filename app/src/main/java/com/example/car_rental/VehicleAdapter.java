package com.example.car_rental;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private ArrayList<VehicleModel> VehicleModels;


    public VehicleAdapter(ArrayList<VehicleModel> model) {
        VehicleModels = model;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView vid;
        private final TextView vdes;
        private final TextView vtype;
        private final TextView vcategory;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            vid = (TextView) view.findViewById(R.id.vid);
            vdes = (TextView) view.findViewById(R.id.vdes);
            vtype = (TextView) view.findViewById(R.id.vtype);
            vcategory = (TextView) view.findViewById(R.id.vcategory);

        }

        public TextView getVid() {
            return vid;
        }

        public TextView getVdes() {
            return vdes;
        }

        public TextView getVtype() { return vtype; }

        public TextView getVcategory() {
            return vcategory;
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_vehicle, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        VehicleModel model = VehicleModels.get(position);
        viewHolder.getVid().setText("Vehicle Id :"+model.getVId());
        viewHolder.getVdes().setText("Description :" +model.getVDes());
        viewHolder.getVcategory().setText("Category :" + model.getVCategory());
        viewHolder.getVtype().setText("Type :" + model.getVtype());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return VehicleModels.size();
    }
}
