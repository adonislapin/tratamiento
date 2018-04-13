package com.adonis.tratamientodeimagenes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adonis.tratamientodeimagenes.R;
import com.adonis.tratamientodeimagenes.mvp.view.IClickInterface;

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.ViewHolder>{

    private int [] accessories;
    private Context context;
    private IClickInterface iClickInterface;

    public AccessoryAdapter(Context context, int [] accessories, IClickInterface iClickInterface){
        this.accessories = accessories;
        this.context = context;
        this.iClickInterface = iClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_accessory, parent, false);
        return new AccessoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int accessory = accessories[position];

        holder.imgAccessory.setTag(accessory);
        holder.imgAccessory.setImageResource(accessory);
    }

    @Override
    public int getItemCount() {
        if(accessories == null){
            return 0;
        } else {
            return accessories.length;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAccessory;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAccessory = itemView.findViewById(R.id.imgAccessory);
            imgAccessory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickInterface != null){
                        iClickInterface.onClickItem((Integer) v.getTag());
                    }
                }
            });
        }
    }
}
