package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class transactionFragment extends Fragment {
    TextView today,yesterday,thisweek,thismonth;
    SqlOpenHelper sqlOpenHelper;
    Cursor mcursor;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tally;
    private long transId=1;

    public transactionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static transactionFragment newInstance(String param1, String param2) {
        transactionFragment fragment = new transactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlOpenHelper=new SqlOpenHelper(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_transaction, container, false);
        today =(TextView)view.findViewById(R.id.today_text);
        yesterday=(TextView)view.findViewById(R.id.yester_text);
        thisweek=(TextView)view.findViewById(R.id.week_text);
        thismonth=(TextView)view.findViewById(R.id.month_text);
        tally=(TextView)view.findViewById(R.id.tally_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragload(transId,sqlOpenHelper);

    }

    public void fragload(long passedid, SqlOpenHelper sqlOpenHelper) {
        String n=null ,y=null,c=null,l=null,f=null,h=null,k=null,b=null,t=null,s=null,w=null;
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = { SqlOpenHelper.KEY_EMP_FOREIGN, SqlOpenHelper.KEY_MONTH, SqlOpenHelper.KEY_WEEK, SqlOpenHelper.KEY_YESTERDAY,SqlOpenHelper.KEY_TODAY,
                SqlOpenHelper.KEY_TALLY};
        mcursor = db.query(SqlOpenHelper.TABLE_SALES, columns, null, null,
                null, null, null);
        int monthPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_MONTH);
        int weekPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_WEEK);
        int yestPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_YESTERDAY);
        int todayPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_TODAY);
        int tallyPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_TALLY);
        long id=mcursor.getColumnIndex(SqlOpenHelper.KEY_EMP_FOREIGN);
        //refresh views here so that they can load again
        while (mcursor.moveToNext()) {
            if(passedid==id){
                n = mcursor.getString(todayPos);
                y = mcursor.getString(weekPos);
                c = mcursor.getString(yestPos);
                l=mcursor.getString(monthPos);
                f=mcursor.getString(tallyPos);
                break;
            }
        }
            today.setText(n);
            yesterday.setText(c);
            thisweek.setText(y);
            thismonth.setText(l);
            tally.setText(f);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}