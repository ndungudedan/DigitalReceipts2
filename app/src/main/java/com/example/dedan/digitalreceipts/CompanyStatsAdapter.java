package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

class CompanyStatsAdapter extends ListAdapter<MonthSalesEntity, CompanyStatsAdapter.StatsHolder> {
    private OnItemClickListener listener;

    public CompanyStatsAdapter() {
        super(diff_Callback);
    }

    @NonNull
    @Override
    public StatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_stats_recycler,parent,false);
        return new StatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsHolder holder, int position) {
        MonthSalesEntity currentmonthsalesEntity=getItem(position);

    }

    private static final DiffUtil.ItemCallback<MonthSalesEntity> diff_Callback=new
            DiffUtil.ItemCallback<MonthSalesEntity>() {

                @Override
                public boolean areItemsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
                    return false;
                }
            };


    public MonthSalesEntity getSaleAt(int position){
        return getItem(position);
    }

    class StatsHolder extends RecyclerView.ViewHolder{
        private TextView txtCash;
        private TextView txtColumn;
        public StatsHolder(@NonNull View itemView) {
            super(itemView);
            txtCash=itemView.findViewById(R.id.cash);
            txtColumn=itemView.findViewById(R.id.column);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(MonthSalesEntity monthSalesEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

