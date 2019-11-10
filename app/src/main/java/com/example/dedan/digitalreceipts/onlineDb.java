package com.example.dedan.digitalreceipts;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class onlineDb {
    FirebaseFirestore db;

    public onlineDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void registerOnline(String userfname,String userscName,int empNO,String userDOB,String userresid,String usermobile,
                               String username,String useremail,String userpass,String usernationalID,String access){
        Map<String, Object> user = new HashMap<>();
        user.put("firstName", userfname);
        user.put("surName", userscName);
        user.put("national_ID", usernationalID);
        user.put("DOB", userDOB);
        user.put("residence", userresid);
        user.put("phoneNo", usermobile);
        user.put("username", username);
        user.put("emailAddress", useremail);
        user.put("password", userpass);
        user.put("empNO", empNO);
        user.put("access",access);

        DocumentReference docRef = db.collection("users").document(userfname+""+userscName);
        docRef.set(user);
    }
}
