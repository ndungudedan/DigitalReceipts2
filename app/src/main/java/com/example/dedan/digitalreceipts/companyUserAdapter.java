package com.example.dedan.digitalreceipts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class companyUserAdapter extends ListAdapter<UserEntity, companyUserAdapter.userHolder> {
    private OnItemClickListener listener;
    protected companyUserAdapter() {
        super(DiffCallback);
    }
    public static final DiffUtil.ItemCallback<UserEntity> DiffCallback=new DiffUtil.ItemCallback<UserEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler,parent,false);
        return new userHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull userHolder holder, int position) {
        UserEntity userEntity=getItem(position);
        holder.txtfname.setText(userEntity.getKEY_FIRSTNAME());
        holder.txtsecname.setText(userEntity.getKEY_SECNAME());
        holder.txtaccess.setText(userEntity.getKEY_ACCESS());
    }
    public UserEntity getUserAt(int position){
        return getItem(position);
    }

    class userHolder extends RecyclerView.ViewHolder {
        private TextView txtfname;private TextView txtsecname;private TextView txtaccess;

        public userHolder(@NonNull View itemView) {
            super(itemView);
            txtfname=itemView.findViewById(R.id.fna);
            txtsecname=itemView.findViewById(R.id.surna);
            txtaccess=itemView.findViewById(R.id.access);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(getUserAt(position));
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(UserEntity userEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}

