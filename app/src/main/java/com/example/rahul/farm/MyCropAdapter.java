package com.example.rahul.farm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.farm.Model.Crop;

import java.util.List;

/**
 * Created by Rahul on 16-04-2017.
 */

public class MyCropAdapter extends RecyclerView.Adapter<MyCropAdapter.MyViewHolder> {

    List<Crop> list;
    Context context;

    public MyCropAdapter(List<Crop> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_crop_card, parent, false);
        return new MyCropAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Crop crop = list.get(position);
        String cropName = crop.cropName;
        String sownDate = crop.sownDate;

        holder.tvCropName.setText(cropName);
        holder.tvSownDate.setText(sownDate);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCropName;
        TextView tvSownDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCropName = (TextView)itemView.findViewById(R.id.cropName);
            tvSownDate = (TextView)itemView.findViewById(R.id.sownDate);

        }
    }
}
