package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.ImageAdapterDeleteProduct;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterBuyProduct extends RecyclerView.Adapter <ImageAdapterBuyProduct.ImageViewHolder> {

    private Context mContext;
    private List<UserProduct> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapterBuyProduct(Context context, List<UserProduct> userUploads) {
        mContext = context;
        mUploads = userUploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item_buy_product, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UserProduct uploadCurrent = mUploads.get(position);
        holder.textViewName.setText("Product Name: " + uploadCurrent.getProName());
        holder.textViewPrice.setText("Price: " + uploadCurrent.getProPrice() + " OMR");
        holder.textViewquantity.setText("Quantity:  " + uploadCurrent.getProQuantity());
        holder.textViewDetail.setText("Details: " + uploadCurrent.getProDetail());
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

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewDetail;
        public TextView textViewquantity;
        public ImageView imageView;
        public Button btn_buy;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.image_view_name2);
            textViewPrice = itemView.findViewById(R.id.image_view_price2);
            textViewquantity = itemView.findViewById(R.id.image_view_quantity2);
            textViewDetail = itemView.findViewById(R.id.image_view_detail2);
            imageView = itemView.findViewById(R.id.image_view_uploaded2);
            btn_buy = itemView.findViewById(R.id.buy_product2);

            btn_buy.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
