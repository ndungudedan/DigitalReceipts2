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

public class customerAdapter extends ListAdapter<CustomerEntity,customerAdapter.customerHolder>{
private OnItemClickListener listener;

    protected customerAdapter() {
        super(diff_Callback);
    }
    public static final DiffUtil.ItemCallback<CustomerEntity> diff_Callback=new
            DiffUtil.ItemCallback<CustomerEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull CustomerEntity oldItem, @NonNull CustomerEntity newItem) {
                    return oldItem.getKEY_id()==newItem.getKEY_id();
                }

                @Override
                public boolean areContentsTheSame(@NonNull CustomerEntity oldItem, @NonNull CustomerEntity newItem) {
                    return oldItem.getKEY_id()==newItem.getKEY_id()&&
                            oldItem.getKEY_address().equals(newItem.getKEY_address()) &&
                            oldItem.getKEY_email().equals(newItem.getKEY_email())&&
                            oldItem.getKEY_first_name().equals(newItem.getKEY_first_name())&&
                            oldItem.getKEY_sec_name().equals(newItem.getKEY_sec_name())&&
                            oldItem.getKEY_location().equals(newItem.getKEY_location())&&
                            oldItem.getKEY_phone().equals(newItem.getKEY_phone()) &&
                            oldItem.getKEY_po_box().equals(newItem.getKEY_po_box());
                }
            };

    @NonNull
    @Override
    public customerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_recycler,parent,false);

        return new customerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull customerHolder holder, int position) {
        CustomerEntity customerEntity=getItem(position);
        holder.txtid.setText(String.valueOf(customerEntity.getKEY_id()));
        holder.txtfname.setText(customerEntity.getKEY_first_name());
        holder.txtsecname.setText(customerEntity.getKEY_sec_name());
        holder.txtpobox.setText(String.valueOf(customerEntity.getKEY_po_box()));
        holder.txtaddress.setText(String.valueOf(customerEntity.getKEY_address()));
        holder.txtlocation.setText(customerEntity.getKEY_location());
        holder.txtphone.setText(String.valueOf(customerEntity.getKEY_phone()));
        holder.txtemail.setText(customerEntity.getKEY_email());
    }
    public CustomerEntity getCustomerAt(int position){
        return getItem(position);
    }

    class customerHolder extends RecyclerView.ViewHolder {
        private TextView txtfname;private TextView txtsecname;
        private TextView txtpobox;private TextView txtid;
        private TextView txtaddress;private TextView txtlocation;
        private TextView txtphone;private TextView txtemail;

        public customerHolder(@NonNull View itemView) {
            super(itemView);
            txtfname=itemView.findViewById(R.id.f_name);txtsecname=itemView.findViewById(R.id.s_name);
            txtpobox=itemView.findViewById(R.id.po_box);txtaddress=itemView.findViewById(R.id.address);
            txtlocation=itemView.findViewById(R.id.location);txtphone=itemView.findViewById(R.id.phone);
            txtemail=itemView.findViewById(R.id.C_email);txtid=itemView.findViewById(R.id.C_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(getCustomerAt(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(CustomerEntity customerEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
