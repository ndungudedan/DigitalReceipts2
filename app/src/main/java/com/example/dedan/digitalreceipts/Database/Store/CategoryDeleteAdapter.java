package com.example.dedan.digitalreceipts.Database.Store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.CustomerEntity;
import com.example.dedan.digitalreceipts.R;
import com.example.dedan.digitalreceipts.customerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryDeleteAdapter extends ListAdapter<CategoryEntity, CategoryDeleteAdapter.categoryHolder> {
    private OnItemClickListener listener;
    public CategoryDeleteAdapter() {
        super(diff_Callback);
    }
    public static final DiffUtil.ItemCallback<CategoryEntity> diff_Callback=new
            DiffUtil.ItemCallback<CategoryEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull CategoryEntity oldItem, @NonNull CategoryEntity newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull CategoryEntity oldItem, @NonNull CategoryEntity newItem) {
                    return false;
                }
            };

    @NonNull
    @Override
    public categoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_delete_recycler,parent,false);
        return new categoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryHolder holder, int position) {
        CategoryEntity categoryEntity=getItem(position);
        holder.txtCat.setText(categoryEntity.getKEY_Category());
    }

    public CategoryEntity getCategoryAt(int position){
        return getItem(position);
    }

    class categoryHolder extends RecyclerView.ViewHolder {
        private TextView txtCat;

        public categoryHolder(@NonNull View itemView) {
            super(itemView);
            txtCat=itemView.findViewById(R.id.cat_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(getCategoryAt(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(CategoryEntity categoryEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
