package com.example.dedan.digitalreceipts.Month_Database.April;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AprViewModel extends AndroidViewModel {
    private AprRepo aprRepo;
    public AprViewModel(@NonNull Application application) {
        super(application);
        aprRepo=new AprRepo(application);
    }
    public void insert(AprEntity aprEntity){
        aprRepo.insert(aprEntity);
    }
    public void update(AprEntity aprEntity){
        aprRepo.update(aprEntity);
    }
}
