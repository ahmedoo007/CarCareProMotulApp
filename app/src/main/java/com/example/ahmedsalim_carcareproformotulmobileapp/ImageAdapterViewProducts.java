package com.example.ahmedsalim_carcareproformotulmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterViewProducts extends RecyclerView.Adapter <ImageAdapterViewProducts.ImageViewHolder>{
    private Context mContext;
    private List<UserProduct> mUploads;

    public ImageAdapterViewProducts(Context context, List<UserProduct> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_view_product, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UserProduct uploadCurrent= mUploads.get(position);
        holder.textViewName.setText("Product Name: "+uploadCurrent.getProName());
        holder.textViewPrice.setText("Price: "+uploadCurrent.getProPrice()+" OMR");
        holder.textViewQuantity.setText("Quantity:  "+uploadCurrent.getProQuantity());
        holder.textViewDetail.setText("Other Details: "+uploadCurrent.getProDetail());
        Picasso.get()
                .load(uploadCurrent.getProUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewDetail;
        public TextView textViewQuantity;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName= itemView.findViewById(R.id.image_view_name);
            textViewPrice= itemView.findViewById(R.id.image_view_price);
            textViewQuantity= itemView.findViewById(R.id.image_view_quantity);
            textViewDetail= itemView.findViewById(R.id.image_view_detail);
            imageView= itemView.findViewById(R.id.image_view_uploaded);


        }


    }
}
