package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class pickedGoodAdapter extends ListAdapter<PickedGoodEntity, pickedGoodAdapter.pickedUnitHolder> {
    private OnItemClickListener listener;

    public pickedGoodAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public pickedGoodAdapter.pickedUnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picked_good_recycler,parent,false);
        return new pickedUnitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull pickedUnitHolder holder, int position) {
        PickedGoodEntity currentEntity=getItem(position);
        holder.txtQuan.setText(String.valueOf(currentEntity.getKEY_Quantity()));
        holder.txtPack.setText(currentEntity.getKEY_Pack());
        holder.txtCost.setText(String.valueOf(currentEntity.getKEY_total()));
        holder.txtItem.setText(currentEntity.getKEY_Item());
    }
    public PickedGoodEntity getGoodAt(int position){
        return getItem(position);
    }

    private static final DiffUtil.ItemCallback<PickedGoodEntity> diffCallback=new
            DiffUtil.ItemCallback<PickedGoodEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull PickedGoodEntity oldItem, @NonNull PickedGoodEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull PickedGoodEntity oldItem, @NonNull PickedGoodEntity newItem) {
                    return false;
                }
            };
    class pickedUnitHolder extends RecyclerView.ViewHolder {
        private TextView txtItem;
        private TextView txtPack;
        private TextView txtCost;
        private TextView txtQuan;

        public pickedUnitHolder(@NonNull View itemView) {
            super(itemView);
            txtItem=itemView.findViewById(R.id.good);
            txtCost=itemView.findViewById(R.id.price);
            txtPack=itemView.findViewById(R.id.packing);
            txtQuan=itemView.findViewById(R.id.quant);

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
        void onItemClick(PickedGoodEntity pickedGoodEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
