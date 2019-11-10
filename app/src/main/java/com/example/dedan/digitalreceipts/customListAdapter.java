package com.example.dedan.digitalreceipts;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class customListAdapter extends ArrayAdapter<load> {
    Context cont;
    int res;
    List<load> rcpt;
    private SparseBooleanArray mSelectedItemsIds;
    private LayoutInflater inflater;

    @Override
    public  void remove(@Nullable load object) {
        rcpt.remove(object);
        notifyDataSetChanged();
    }

    public customListAdapter(@NonNull Context context, int resource, @NonNull List<load> objects) {
        super(context, resource, objects);
        mSelectedItemsIds = new  SparseBooleanArray();
        this.cont=context;
        this.rcpt=objects;
        this.res=resource;
        inflater = LayoutInflater.from(cont);
    }
    public class viewHolder{
        TextView txt;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewholder;
        if(convertView==null){
            viewholder = new viewHolder();
            convertView = inflater.inflate(R.layout.adapter_view, null);
            viewholder.txt = convertView.findViewById(R.id.rcpt);
            convertView.setTag(viewholder);
        }
        else{
            viewholder = (viewHolder) convertView.getTag();
        }
        load load=rcpt.get(position);
        viewholder.txt.setText(load.getFilename());
        return convertView;
    }
    public  List<load> getMyList() {
        return rcpt;
    }
    public void  toggleSelection(int position) {

        selectView(position, !mSelectedItemsIds.get(position));

    }
    public void  removeSelection() {

        mSelectedItemsIds = new  SparseBooleanArray();

        notifyDataSetChanged();

    }
    public void selectView(int position, boolean value) {

        if (value)
            mSelectedItemsIds.put(position,  value);

        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();

    }
    public int  getSelectedCount() {

        return mSelectedItemsIds.size();

    }



    public SparseBooleanArray getSelectedIds() {

        return mSelectedItemsIds;

    }
}
