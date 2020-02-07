package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class company_user_fragment extends Fragment {
    UserViewModel userViewModel;
    private companyUserAdapter adapter;

    public company_user_fragment() {
        // Required empty public constructor
    }
    public static company_user_fragment newInstance(String param1, String param2) {
        company_user_fragment fragment = new company_user_fragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        adapter = new companyUserAdapter();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_company_user_fragment, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.userRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                adapter.submitList(userEntities);
            }
        });
        adapter.setOnItemClickListener(new companyUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserEntity userEntity) {
                Intent intent=new Intent(getActivity(),security.class);
                intent.putExtra("userid",userEntity.getKEY_USER_ID());
                startActivity(intent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
