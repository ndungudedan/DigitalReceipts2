package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class analytics_user extends Fragment {
    TextView today,yesterday,thisweek,thismonth;
    SqlOpenHelper sqlOpenHelper;
    Cursor mcursor;
    private TextView tally;
    public long transId=1;
    long current_empNo=0;
    public String user;
    SharedPreferences sharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static analytics_user fragment;

    public analytics_user() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static analytics_user newInstanceAnalytics (Long param1) {
        fragment = new analytics_user();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlOpenHelper=new SqlOpenHelper(getActivity());
        sharedPreferences=getContext().getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        user = sharedPreferences.getString("current_username","");
        current_empNo=sharedPreferences.getLong("current_empNo",0);
        if (getArguments() != null) {
            transId = getArguments().getLong(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_analytics_user, container, false);
        today = view.findViewById(R.id.today_text);
        yesterday= view.findViewById(R.id.yester_text);
        thisweek= view.findViewById(R.id.week_text);
        thismonth= view.findViewById(R.id.month_text);
        tally= view.findViewById(R.id.tally_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!user.startsWith("ADMIN")){
            fragload(current_empNo,sqlOpenHelper);
        }
        else{
            fragload(transId,sqlOpenHelper);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        int idPos=mcursor.getColumnIndex(SqlOpenHelper.KEY_EMP_FOREIGN);
        //refresh views here so that they can load again
        while (mcursor.moveToNext()) {
            if(passedid==mcursor.getLong(idPos)){
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
