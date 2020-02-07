package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MonthTotalAdapter extends ListAdapter<MonthSalesEntity,MonthTotalAdapter.MonthStatsHolder> {
    public MonthTotalAdapter(){
        super(diff_Callback);
    }
    private static final DiffUtil.ItemCallback<MonthSalesEntity> diff_Callback=new DiffUtil.ItemCallback<MonthSalesEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
            return oldItem.getKEY_total()==newItem.getKEY_total();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
            return oldItem.getKEY_total()==newItem.getKEY_total()&&
                    oldItem.getKEY_time().equals(newItem.getKEY_time());
        }
    };

    @NonNull
    @Override
    public MonthStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.month_total_recycler,parent,false);
        return new MonthStatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthStatsHolder holder, int position) {
        MonthSalesEntity monthSalesEntity=getItem(position);
        holder.txtCash.setText(String.valueOf(monthSalesEntity.getKEY_total()));
        holder.txtColumn.setText(monthSalesEntity.getKEY_time());
    }

    class MonthStatsHolder extends RecyclerView.ViewHolder{
        private TextView txtCash;
        private TextView txtColumn;
        public MonthStatsHolder(@NonNull View itemView) {
            super(itemView);
            txtCash=itemView.findViewById(R.id.info);
            txtColumn=itemView.findViewById(R.id.time);

        }
    }
}
