package com.example.ahmedsalim_carcareproformotulmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapterViewAppointment extends RecyclerView.Adapter <ImageAdapterViewAppointment.ImageViewHolder>{
    private Context mContext;
    private List<UserAppointment> mUploads;

    public ImageAdapterViewAppointment(Context context, List<UserAppointment> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_view_appointment, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UserAppointment uploadCurrent= mUploads.get(position);
        holder.textbookingService.setText("Booked Service: "+uploadCurrent.getSerName());
        holder.textBookingUsername.setText("User Name: "+uploadCurrent.getSerUsername());
        holder.textBookingDate.setText("Appointment Date:  "+uploadCurrent.getSerDate());
        holder.textBookingTime.setText("Appointment Time: "+uploadCurrent.getSerTime());
        holder.textBookingPhone.setText("Phone number: "+uploadCurrent.getSerPhone());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textbookingService;
        public TextView textBookingUsername;
        public TextView textBookingTime;
        public TextView textBookingDate;
        public TextView textBookingPhone;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textbookingService= itemView.findViewById(R.id.bookService);
            textBookingUsername= itemView.findViewById(R.id.bookUsername);
            textBookingDate= itemView.findViewById(R.id.bookDate);
            textBookingTime= itemView.findViewById(R.id.bookTime);
            textBookingPhone= itemView.findViewById(R.id.bookPhone);

        }
    }
}
