package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class secFragment extends Fragment implements View.OnClickListener {
    UserViewModel userViewModel;

    EditText user_fName,user_scName,user_idNo,user_DOB,user_resid,user_mobileNo,user_email;
    TextInputEditText txt_pass;
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
    private RecyclerView recyclerView;
    private CompanyStatsAdapter adapter;
    private int LoggedUserId;

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
        LoggedUserId=sharedPreferences.getInt("current_userId",0);


        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sec, container, false);
        recyclerView = view.findViewById(R.id.userRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CompanyStatsAdapter();
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                adapter.submitList(userEntities);
            }
        });
        adapter.setOnItemClickListener(new CompanyStatsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserEntity userEntity) {
                /*Intent intent=new Intent();
                intent.putExtra("userid",LoggedUserId);
                startActivity(intent);*/
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null){
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

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

           //update local database
        }
        else{
            Toast.makeText(getActivity(), "failed!!!No network access",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return (Runtime.getRuntime().exec(command).waitFor(5, TimeUnit.SECONDS));
            }
            return (Runtime.getRuntime().exec(command).waitFor()==0);
        } catch (Exception e) {
            return false;
        }
    }
}
