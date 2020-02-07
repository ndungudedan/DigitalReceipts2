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
        private TextView txtfna;
        private TextView txtsurna;

        public userStatsHolder(@NonNull View itemView) {
            super(itemView);
            txtfna=itemView.findViewById(R.id.fna);
            txtsurna=itemView.findViewById(R.id.surna);

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
