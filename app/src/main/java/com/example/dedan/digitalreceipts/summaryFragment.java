package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class summaryFragment extends Fragment {
    SqlOpenHelper sqlOpenHelper;
    Cursor mcursor;
    TextView tally,today,yesterday,week,month;
    private Cursor cursor;

    public summaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlOpenHelper=new SqlOpenHelper(getActivity());
    }
    public void getSales(){
        String p="";String n="";String l="";String h="";String c="";
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_MONTH_COMPANY,SqlOpenHelper.KEY_WEEK_COMPANY,
                SqlOpenHelper.KEY_YESTERDAY_COMPANY, SqlOpenHelper.KEY_TODAY_COMPANY,SqlOpenHelper.KEY_TALLY_COMPANY};
        cursor = db.query(SqlOpenHelper.TABLE_COMPANY_ANALYSIS, columns, null, null,
                null, null, null);

        int yesterdayPos = cursor.getColumnIndex(SqlOpenHelper.KEY_YESTERDAY_COMPANY);
        int weekPos = cursor.getColumnIndex(SqlOpenHelper.KEY_WEEK_COMPANY);
        int todayPos = cursor.getColumnIndex(SqlOpenHelper.KEY_TODAY_COMPANY);
        int monthPos = cursor.getColumnIndex(SqlOpenHelper.KEY_MONTH_COMPANY);
        int tallyPos = cursor.getColumnIndex(SqlOpenHelper.KEY_TALLY_COMPANY);
        //refresh views here so that they can load again

        while (cursor.moveToNext()){
            n = cursor.getString(yesterdayPos);
            c = cursor.getString(weekPos);
            l = cursor.getString(todayPos);
            p = cursor.getString(monthPos);
            h = cursor.getString(tallyPos);
        }
        tally.setText(h);
        today.setText(l);
        yesterday.setText(n);
        week.setText(c);
        month.setText(p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        tally=view.findViewById(R.id.tally_text);
        today=view.findViewById(R.id.today_text);
        yesterday=view.findViewById(R.id.yester_text);
        week=view.findViewById(R.id.week_text);
        month=view.findViewById(R.id.month_text);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSales();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cursor.close();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
