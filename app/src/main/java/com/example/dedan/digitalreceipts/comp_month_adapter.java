package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class comp_month_adapter extends ListAdapter<MonthSalesEntity, comp_month_adapter.saleholder> {

    public comp_month_adapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<MonthSalesEntity> diffCallback=new DiffUtil.ItemCallback<MonthSalesEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MonthSalesEntity oldItem, @NonNull MonthSalesEntity newItem) {
            return false;
        }
    };
    @NonNull
    @Override
    public saleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.month_total_recycler,parent,false);
        return new saleholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull saleholder holder, int position) {
        MonthSalesEntity monthSalesEntity=getItem(position);
        holder.txtCash.setText(String.valueOf(monthSalesEntity.getKEY_total()));
        holder.txtColumn.setText(monthSalesEntity.getKEY_time());
        holder.txtClient.setText(String.valueOf(monthSalesEntity.getKEY_clients()));
    }

    class saleholder extends RecyclerView.ViewHolder {
        private TextView txtCash;
        private TextView txtClient;
        private TextView txtColumn;
        public saleholder(@NonNull View itemView) {
            super(itemView);
            txtCash=itemView.findViewById(R.id.info);
            txtColumn=itemView.findViewById(R.id.time);
            txtClient=itemView.findViewById(R.id.clientele);
        }
    }
}
