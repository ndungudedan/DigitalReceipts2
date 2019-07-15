package com.example.dedan.digitalreceipts;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;


public class secFragment extends Fragment implements View.OnClickListener {
    EditText user_fName,user_scName,user_idNo,user_DOB,user_resid,user_mobileNo,user_email;
    EditText txt_user;
    EditText txt_pass;
    TextView user_empno;
    Cursor mcursor;
    security security;
    SqlOpenHelper sqlOpenHelper;
    SharedPreferences sharedPreferences;
    public String user;
    public long hid=1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static secFragment fragment;
    String accessCheck=null;
    RelativeLayout relative;
    private Button authenticate;
    private Button invalidate;

    private FirebaseAuth mAuth;

    public secFragment() {
    }

    public static Fragment newInstance(long param1,String userCheck) {
        fragment = new secFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,userCheck);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getContext().getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        user = sharedPreferences.getString("current_username","");
        sqlOpenHelper=new SqlOpenHelper(getActivity());
        security=new security();
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            hid = getArguments().getLong(ARG_PARAM1);
            accessCheck= getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //container.removeAllViews();
        //container.removeAllViewsInLayout();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sec, container, false);
        RelativeLayout relativeLayout=view.findViewById(R.id.rel);
        txt_user = view.findViewById(R.id.user_txt);
        txt_pass =view. findViewById(R.id.pass_txt);
        user_fName=view.findViewById(R.id.user_first_name);
        user_scName=view.findViewById(R.id.user_sec_name);
        user_DOB=view.findViewById(R.id.user_dob);
        user_idNo=view.findViewById(R.id.user_id_no);
        user_mobileNo=view.findViewById(R.id.user_tel_no);
        user_resid=view.findViewById(R.id.user_residence);
        user_email=view.findViewById(R.id.user_email_reg);
        user_empno=view.findViewById(R.id.empno_text);
        authenticate = (Button)view.findViewById(R.id.auth_btn);
        invalidate = (Button)view.findViewById(R.id.inval_btn);
        Button edit=(Button)view.findViewById(R.id.edit_btn);
        Button save=(Button)view.findViewById(R.id.save_btn);

        if (getArguments() != null) {
            user_fName.getText().clear();
            user_scName.getText().clear();
        }
        if(hid>1){
            relativeLayout.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);

            if(accessCheck.equals("ACCESS_GRANTED")){
                invalidate.setVisibility(View.VISIBLE);
                invalidate.setOnClickListener(this);
                authenticate.setVisibility(View.INVISIBLE);
            }
            else{
                authenticate.setVisibility(View.VISIBLE);
                invalidate.setVisibility(View.INVISIBLE);
                authenticate.setOnClickListener(this);
            }
        }
        else{
            authenticate.setVisibility(View.INVISIBLE);
            invalidate.setVisibility(View.INVISIBLE);
        }
        edit.setOnClickListener(this);
        save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!user.startsWith("ADMIN")){
            fragload(0,sqlOpenHelper);
        }
        else{
            fragload(hid,sqlOpenHelper);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void fragload(long passedid, SqlOpenHelper sqlOpenHelper) {
        String n=null ,y=null,c=null,l=null,f=null,h=null,k=null,b=null,t=null,s=null,w=null;
        String passeduser = user;
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper._USERID, SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME, SqlOpenHelper.KEY_PASS, SqlOpenHelper.KEY_EMAIL
                , SqlOpenHelper.KEY_FIRSTNAME, SqlOpenHelper.KEY_DOB, SqlOpenHelper.KEY_SECNAME,
                SqlOpenHelper.KEY_residence, SqlOpenHelper.KEY_MOBILENO, SqlOpenHelper.KEY_empNO,SqlOpenHelper.KEY_NATNLID};
        mcursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);
        int IdPos = mcursor.getColumnIndex(SqlOpenHelper._USERID);
        int namePos = mcursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int passPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
        int empPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_empNO);
        int emailPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_EMAIL);
        int logPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_LOG);
        int fnamePos = mcursor.getColumnIndex(SqlOpenHelper.KEY_FIRSTNAME);
        int secPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_SECNAME);
        int resPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_residence);
        int dobPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_DOB);
        int mobilePos = mcursor.getColumnIndex(SqlOpenHelper.KEY_MOBILENO);
        int natnlidPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_NATNLID);
        //refresh views here so that they can load again
        while (mcursor.moveToNext()) {
            long id = mcursor.getLong(IdPos);
            if(passedid!=0) {
                if (id == passedid) {
                     n = mcursor.getString(namePos);
                     y = mcursor.getString(passPos);
                     c = mcursor.getString(emailPos);
                     f = mcursor.getString(fnamePos);
                     h = mcursor.getString(secPos);
                     k = mcursor.getString(resPos);
                     b = mcursor.getString(dobPos);
                     t = mcursor.getString(mobilePos);
                     s = mcursor.getString(natnlidPos);
                     w=mcursor.getString(empPos);

                  //  refresh();
                    break;
                }
            }
else {
                 n = mcursor.getString(namePos);
                if (n.equals(passeduser)) {
                     y = mcursor.getString(passPos);
                     c = mcursor.getString(emailPos);
                     f = mcursor.getString(fnamePos);
                     h = mcursor.getString(secPos);
                     k = mcursor.getString(resPos);
                     b = mcursor.getString(dobPos);
                     t = mcursor.getString(mobilePos);
                     s = mcursor.getString(natnlidPos);
                     w=mcursor.getString(empPos);
                     break;
                }
            }
    }
    if(txt_user!=null){
        txt_user.setText(n);
        txt_pass.setText(y);
        user_email.setText(c);
        user_fName.setText(f);
        user_scName.setText(h);
        user_idNo.setText(s);
        user_resid.setText(k);
        user_DOB.setText(b);
        user_mobileNo.setText(t);
        user_empno.setText(w);
    }
                }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_btn:
                setEditable();
                break;
            case R.id.save_btn:
                update();
                notEditable();
                break;
            case R.id.auth_btn:
                authorise();
                break;
            case R.id.inval_btn:
                invalidate();
                break;
        }
    }
    public void update(){
        if(internetIsConnected()){
            FirebaseUser fireuser = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword =txt_pass.getText().toString().trim();
            fireuser.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "password updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            fireuser.updateEmail(user_email.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "email updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            String select=SqlOpenHelper.KEY_ID+"=?";
            String[]selectArgs={user};
            ContentValues values=new ContentValues();
            values.put(SqlOpenHelper.KEY_NAME,txt_user.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_PASS,txt_pass.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_FIRSTNAME,user_fName.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_SECNAME,user_scName.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_MOBILENO,user_mobileNo.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_residence,user_resid.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_DOB,user_DOB.getText().toString().trim());
            values.put(SqlOpenHelper.KEY_EMAIL,user_email.getText().toString().trim());
            SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
            db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
        }
        else{
            Toast.makeText(getActivity(), "failed!!!No network access",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void notEditable(){
        txt_user.setEnabled(false);
        txt_pass.setEnabled(false);
        user_email.setEnabled(false);
        user_DOB.setEnabled(false);
        user_resid.setEnabled(false);
        user_mobileNo.setEnabled(false);
        user_fName.setEnabled(false);
        user_scName.setEnabled(false);
        user_idNo.setEnabled(false);
    }
    public void setEditable(){
        txt_user.setEnabled(true);
        txt_pass.setEnabled(true);
        user_email.setEnabled(true);
        user_DOB.setEnabled(true);
        user_resid.setEnabled(true);
        user_mobileNo.setEnabled(true);
        user_fName.setEnabled(true);
        user_scName.setEnabled(true);
    }
    public void invalidate() {
        String select = SqlOpenHelper._USERID + "=?";
        String[] selectArgs = {String.valueOf(hid)};
        ContentValues values = new ContentValues();
        values.put(SqlOpenHelper.KEY_ACCESS, "ACCESS_DENIED");
        SQLiteDatabase db = sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER, values, select, selectArgs);
        invalidate.setVisibility(View.INVISIBLE);
        authenticate.setVisibility(View.VISIBLE);
    }
    public void authorise() {
        String select = SqlOpenHelper._USERID + "=?";
        String[] selectArgs = {String.valueOf(hid)};
        ContentValues values = new ContentValues();
        values.put(SqlOpenHelper.KEY_ACCESS, "ACCESS_GRANTED");
        SQLiteDatabase db = sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER, values, select, selectArgs);
        authenticate.setVisibility(View.INVISIBLE);
        invalidate.setVisibility(View.VISIBLE);
    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}
