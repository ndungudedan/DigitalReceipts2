package com.example.dedan.digitalreceipts;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReceiptAdapter extends ListAdapter<ReceiptEntity, ReceiptAdapter.ReceiptHolder> {
    private OnItemClickListener listener;

    public ReceiptAdapter() {
        super(diff_Callback);
    }

    @NonNull
    @Override
    public ReceiptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.receipts_recycler,parent,false);
        return new ReceiptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptHolder holder, int position) {
        ReceiptEntity rcptEntity=getItem(position);
        holder.txtRef.setText(rcptEntity.getKEY_customer());
        holder.txtdate.setText(rcptEntity.getKEY_date());
        holder.txtuser.setText(rcptEntity.getKEY_servedby());
    }

    private static final DiffUtil.ItemCallback<ReceiptEntity> diff_Callback=new
            DiffUtil.ItemCallback<ReceiptEntity>() {

                @Override
                public boolean areItemsTheSame(@NonNull ReceiptEntity oldItem, @NonNull ReceiptEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull ReceiptEntity oldItem, @NonNull ReceiptEntity newItem) {
                    return false;
                }
            };


    public ReceiptEntity getReceiptAt(int position){
        return getItem(position);
    }

    class ReceiptHolder extends RecyclerView.ViewHolder{
        private TextView txtRef;
        private TextView txtdate;
        private TextView txtuser;
        public ReceiptHolder(@NonNull View itemView) {
            super(itemView);
            txtRef=itemView.findViewById(R.id.txt_rcpt_ref);
            txtdate=itemView.findViewById(R.id.txt_rcpt_date);
            txtuser=itemView.findViewById(R.id.txt_rcpt_user);

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
        void onItemClick(ReceiptEntity receiptEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
