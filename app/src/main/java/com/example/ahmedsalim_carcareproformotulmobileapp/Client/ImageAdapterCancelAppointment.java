package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmedsalim_carcareproformotulmobileapp.ImageAdapterViewAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserAppointment;

import java.util.List;

public class ImageAdapterCancelAppointment extends RecyclerView.Adapter <ImageAdapterCancelAppointment.ImageViewHolder>{
    private Context mContext;
    private List<UserAppointment> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapterCancelAppointment(Context context, List<UserAppointment> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_cancel_appointment, parent, false);
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

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textbookingService;
        public TextView textBookingUsername;
        public TextView textBookingTime;
        public TextView textBookingDate;
        public TextView textBookingPhone;
        public Button btn_delete;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textbookingService= itemView.findViewById(R.id.bookService1);
            textBookingUsername= itemView.findViewById(R.id.bookUsername1);
            textBookingDate= itemView.findViewById(R.id.bookDate1);
            textBookingTime= itemView.findViewById(R.id.bookTime1);
            textBookingPhone= itemView.findViewById(R.id.bookPhone1);

            btn_delete= itemView.findViewById(R.id.btn_delAppointment);

            btn_delete.setOnClickListener(this);

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
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }
}
