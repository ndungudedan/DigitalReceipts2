package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserStatsAdapter extends ListAdapter<UserStatsEntity,UserStatsAdapter.userStatsHolder> {
    private OnItemClickListener listener;

    protected UserStatsAdapter() {
        super(diff_Callback);
    }

    @NonNull
    @Override
    public userStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_stats_recycler,parent,false);
        return new userStatsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userStatsHolder holder, int position) {
        UserStatsEntity userStatsEntity=getUserStatAt(position);
        holder.txtdate.setText(userStatsEntity.getKEY_WEEK());
        holder.txtsale.setText(String.valueOf(userStatsEntity.getKEY_WEEK_SALES()));
        holder.txtclient.setText(String.valueOf(userStatsEntity.getKEY_W_CLIENTS_SERVED()));
    }

    public static final DiffUtil.ItemCallback<UserStatsEntity> diff_Callback=new
            DiffUtil.ItemCallback<UserStatsEntity>() {

                @Override
                public boolean areItemsTheSame(@NonNull UserStatsEntity oldItem, @NonNull UserStatsEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserStatsEntity oldItem, @NonNull UserStatsEntity newItem) {
                    return false;
                }
            };

    public UserStatsEntity getUserStatAt(int position){
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
        void onItemClick(UserStatsEntity userStatsEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
