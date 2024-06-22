package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserFeedback;


import java.util.List;

public class ImageAdapterViewFeedback extends RecyclerView.Adapter <ImageAdapterViewFeedback.ImageViewHolder>{
    private Context mContext;
    private List<UserFeedback> mUploads;

    public ImageAdapterViewFeedback(Context context, List<UserFeedback> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_view_feedback, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UserFeedback uploadCurrent= mUploads.get(position);
        holder.textFeedName.setText("Service Name: "+uploadCurrent.getFeedName());
        holder.textFeedRating.setText("Price: "+uploadCurrent.getFeedRating());
        holder.textFeedComment.setText("Booking Time: "+uploadCurrent.getFeedComment());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textFeedName;
        public TextView textFeedRating;
        public TextView textFeedComment;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textFeedName= itemView.findViewById(R.id.f_ser_name);
            textFeedRating= itemView.findViewById(R.id.f_ser_rating);
            textFeedComment= itemView.findViewById(R.id.f_ser_comment);
        }

    }
}
