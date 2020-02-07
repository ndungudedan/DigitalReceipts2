package com.example.dedan.digitalreceipts.Database.Today_Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.R;
import com.example.dedan.digitalreceipts.UserStatsAdapter;
import com.example.dedan.digitalreceipts.UserStatsEntity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserStatsMonthAdapter extends ListAdapter<UserStatsMonthEntity, UserStatsMonthAdapter.userStatsHolder> {
    private OnItemClickListener listener;

    public UserStatsMonthAdapter() {
        super(diff_Callback);
    }

    @NonNull
    @Override
    public userStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_stats_recycler,parent,false);
        return new userStatsHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull userStatsHolder holder, int position) {
        UserStatsMonthEntity userStatsMonthEntity=getUserStatAt(position);
        holder.txtdate.setText(userStatsMonthEntity.getKEY_MONTH());
        holder.txtsale.setText(String.valueOf(userStatsMonthEntity.getKEY_MONTH_SALES()));
        holder.txtclient.setText(String.valueOf(userStatsMonthEntity.getKEY_M_CLIENTS_SERVED()));
    }

    public static final DiffUtil.ItemCallback<UserStatsMonthEntity> diff_Callback=new
            DiffUtil.ItemCallback<UserStatsMonthEntity>() {

                @Override
                public boolean areItemsTheSame(@NonNull UserStatsMonthEntity oldItem, @NonNull UserStatsMonthEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserStatsMonthEntity oldItem, @NonNull UserStatsMonthEntity newItem) {
                    return false;
                }
            };

    public UserStatsMonthEntity getUserStatAt(int position){
        return getItem(position);
    }

    class userStatsHolder extends RecyclerView.ViewHolder {
        private TextView txtdate;
        private TextView txtsale;
        private TextView txtclient;

        public userStatsHolder(@NonNull View itemView) {
            super(itemView);
            txtdate=itemView.findViewById(R.id.date);
            txtclient=itemView.findViewById(R.id.client_served);
            txtsale=itemView.findViewById(R.id.sales);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(getUserStatAt(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(UserStatsMonthEntity userStatsMonthEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}

