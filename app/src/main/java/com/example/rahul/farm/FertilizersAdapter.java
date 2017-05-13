package com.example.rahul.farm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rahul.farm.Model.Crop;
import com.example.rahul.farm.Model.Fertilizer;

import java.util.List;

/**
 * Created by Rahul on 25-04-2017.
 */

public class FertilizersAdapter extends RecyclerView.Adapter<FertilizersAdapter.MyViewHolder> {

    List<Fertilizer> list;
    Context context;

    public FertilizersAdapter(List<Fertilizer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fertilizers_used_card, parent, false);
        return new FertilizersAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Fertilizer fert = list.get(position);
        String type = fert.type;
        String Nitrogenous = fert.Nitrogenous;
        String Phosphatic = fert.Phosphatic;
        String Potassic = fert.Potassic;
        String Manure = fert.Manure;


        holder.tvtype.setText(type);
        holder.tvNitrogenous.setText(Nitrogenous);
        holder.tvPhosphatic.setText(Phosphatic);
        holder.tvPotassic.setText(Potassic);
        holder.tvManure.setText(Manure);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtype;
        TextView tvNitrogenous;
        TextView tvPhosphatic;
        TextView tvPotassic;
        TextView tvManure;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvtype = (TextView)itemView.findViewById(R.id.cropName);
            tvNitrogenous = (TextView)itemView.findViewById(R.id.N_value);
            tvPhosphatic = (TextView)itemView.findViewById(R.id.Ph_value);
            tvPotassic = (TextView)itemView.findViewById(R.id.Po_value);
            tvManure = (TextView)itemView.findViewById(R.id.M_value);
        }
    }
}
