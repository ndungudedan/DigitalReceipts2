package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.R;
import com.example.dedan.digitalreceipts.noteItem;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<noteItem> mNotes;

    public RecyclerAdapter(Context mContext, List<noteItem> mNotes) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mNotes = mNotes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView quan;
        public final TextView item;
        public final TextView each;
        public final TextView cost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quan=(TextView)itemView.findViewById(R.id.txt_quan);
            item=(TextView)itemView.findViewById(R.id.txt_item);
            each=(TextView)itemView.findViewById(R.id.txt_each);
            cost=(TextView)itemView.findViewById(R.id.txt_cost);

        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.recycler,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder viewHolder, int i) {
        noteItem note=mNotes.get(i);
        viewHolder.quan.setText(note.getQuantity());
        viewHolder.item.setText(note.getItem());
        viewHolder.each.setText(note.getEach());
        viewHolder.cost.setText(note.getCost());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


}
