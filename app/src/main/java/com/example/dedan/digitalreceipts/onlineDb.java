package com.example.dedan.digitalreceipts;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class onlineDb {
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    private Task<Uri> downUrl;

    public onlineDb(FirebaseFirestore db,FirebaseStorage storage,StorageReference storageRef) {
        this.storage=storage;
        this.db = db;
        this.storageRef=storageRef;
    }

    public void registerOnline(String userfname,String userscName,int empNO,String userDOB,String userresid,String usermobile,
                               String username,String useremail,String userpass,String usernationalID,String access,String picpath){
        Map<String, Object> user = new HashMap<>();
        user.put("picUrl",userPic(username,picpath));
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
        user.put("accessbtn",access);

        DocumentReference docRef = db.collection("users").document(userfname+""+userscName);
        docRef.set(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore fail", " "+e.getLocalizedMessage());
            }
        });
    }
    public String userPic(String username,String filepath){
        Uri file = Uri.fromFile(new File(filepath));
        final StorageReference userImagesRef = storageRef.child("userPics/"+username+".jpg");
        UploadTask uploadTask = userImagesRef.putFile(file);
// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("profilePic", " "+exception.getLocalizedMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                downUrl =userImagesRef.getDownloadUrl();
            }
        });
        return downUrl.toString();
    }

    public void updateOnline(String userfname, String userscName, int empNO, String userDOB, String userresid, String usermobile,
                             String username, String useremail, String userpass, String usernationalID, String access,String picpath) {
        Map<String, Object> user = new HashMap<>();
        user.put("picUrl",userPic(username,picpath));
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
        user.put("accessbtn",access);
        DocumentReference docRef = db.collection("users").document(userfname+""+userscName);
        docRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("firestore update", " success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore update", " failed");
            }
        });
    }
    //Company Online functions
    public void registerCompanyOnline(String name,int mnthpos,String month,String location,String mobile,String email,String picpath){
        Map<String, Object> company = new HashMap<>();
        company.put("picUrl",companyPic(name,picpath));
        company.put("companyName", name);
        company.put("location", location);
        company.put("phoneNo", mobile);
        company.put("emailAddress", email);
        company.put("financialMonthPos", mnthpos);
        company.put("financialMonth", month);

        DocumentReference docRef = db.collection("companies").document(name);
        docRef.set(company).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore fail", " "+e.getLocalizedMessage());
            }
        });
    }
    public String companyPic(String name,String filepath){
        Uri file = Uri.fromFile(new File(filepath));
        final StorageReference ImagesRef = storageRef.child("companyPics/"+name+".jpg");
        UploadTask uploadTask = ImagesRef.putFile(file);
// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("profilePic", " "+exception.getLocalizedMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                downUrl =ImagesRef.getDownloadUrl();
            }
        });
        return downUrl.toString();
    }

    public void updateCompanyOnline(String name,int mnthpos,String month,String location,String mobile,String email,String picpath) {
        Map<String, Object> company = new HashMap<>();
        company.put("picUrl",companyPic(name,picpath));
        company.put("companyName", name);
        company.put("location", location);
        company.put("phoneNo", mobile);
        company.put("emailAddress", email);
        company.put("financialMonthPos", mnthpos);
        company.put("financialMonth", month);

        DocumentReference docRef = db.collection("companies").document(name);
        docRef.update(company).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("firestore update", " success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore update", " failed");
            }
        });
    }
    public void companyUsers(String companyname,String userEmail,String username){
        Map<String, Object> company = new HashMap<>();
        company.put("name", username);
        DocumentReference docRef = db.collection("companies").document(companyname).collection("companyUsers").document(userEmail);
        docRef.set(company).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore fail", " "+e.getLocalizedMessage());
            }
        });
    }
    public void companyUpdateUsers(String companyname,String userEmail,String username){
        Map<String, Object> company = new HashMap<>();
        company.put("name", username);
        DocumentReference docRef = db.collection("companies").document(companyname).collection("companyUsers").document(userEmail);
        docRef.update(company).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firestore fail", " "+e.getLocalizedMessage());
            }
        });
    }

}
