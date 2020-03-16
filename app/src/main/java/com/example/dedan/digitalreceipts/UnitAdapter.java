package com.example.dedan.digitalreceipts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import androidx.annotation.DrawableRes;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
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
                .inflate(R.layout.unit_recycler,parent,false); //home activity uses home_unit_recycler
        return new UnitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitAdapter.UnitHolder holder, int position) {
        GoodsEntity currentgoodsEntity=getItem(position);
        holder.imageView.setImageBitmap(retreivePic(currentgoodsEntity.getKEY_PIC(),currentgoodsEntity.getKEY_ITEM()));
        holder.txtItem.setText(currentgoodsEntity.getKEY_ITEM());
        holder.txtCost.setText(String.valueOf(currentgoodsEntity.getKEY_COST()));
        holder.txtPack.setText(currentgoodsEntity.getKEY_PACK());
        holder.txtCat.setText(currentgoodsEntity.getKEY_CATEGORY());
    }
    public GoodsEntity getGoodAt(int position){
        return getItem(position);
    }

    class UnitHolder extends RecyclerView.ViewHolder{
        private TextView txtItem;private TextView txtCat;
        private TextView txtPack;private TextView txtCost;
        private ImageView imageView;
        public UnitHolder(@NonNull View itemView) {
            super(itemView);
            txtItem=itemView.findViewById(R.id.txt_item);
            txtCost=itemView.findViewById(R.id.txt_cost);
            txtPack=itemView.findViewById(R.id.txt_pack);
            txtCat=itemView.findViewById(R.id.txt_cat);
            imageView=itemView.findViewById(R.id.unit_recycler_image);

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
    private Bitmap retreivePic(String path,String item) {
        Bitmap bit=null;
        try{
            File file=new File(path,item+"pic.jpg");
                bit= BitmapFactory.decodeStream(new FileInputStream(file));

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return bit;
    }
}
