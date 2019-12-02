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

class UnitAdapter extends ListAdapter<GoodsEntity,UnitAdapter.UnitHolder> {
    private OnItemClickListener listener;

    public UnitAdapter() {
        super(diff_Callback);
    }
    private static final DiffUtil.ItemCallback<GoodsEntity> diff_Callback=new
            DiffUtil.ItemCallback<GoodsEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull GoodsEntity oldItem, @NonNull GoodsEntity newItem) {
                    return oldItem.getKEY_GOODS_ID()==newItem.getKEY_GOODS_ID();
                }

                @Override
                public boolean areContentsTheSame(@NonNull GoodsEntity oldItem, @NonNull GoodsEntity newItem) {
                    return oldItem.getKEY_ITEM().equals(newItem.getKEY_ITEM())&&
                            oldItem.getKEY_COST()==(newItem.getKEY_COST())&&
                            oldItem.getKEY_PACK().equals(newItem.getKEY_PACK());
                }
            };

    @NonNull
    @Override
    public UnitAdapter.UnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_recycler,parent,false);
        return new UnitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitAdapter.UnitHolder holder, int position) {
        GoodsEntity currentgoodsEntity=getItem(position);
        holder.txtItem.setText(currentgoodsEntity.getKEY_ITEM());
        holder.txtCost.setText(String.valueOf(currentgoodsEntity.getKEY_COST()));
        holder.txtPack.setText(currentgoodsEntity.getKEY_PACK());
    }
    public GoodsEntity getGoodAt(int position){
        return getItem(position);
    }

    class UnitHolder extends RecyclerView.ViewHolder{
        private TextView txtItem;
        private TextView txtPack;
        private TextView txtCost;
        public UnitHolder(@NonNull View itemView) {
            super(itemView);
            txtItem=itemView.findViewById(R.id.txt_item);
            txtCost=itemView.findViewById(R.id.txt_cost);
            txtPack=itemView.findViewById(R.id.txt_pack);

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
        void onItemClick(GoodsEntity goodsEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
