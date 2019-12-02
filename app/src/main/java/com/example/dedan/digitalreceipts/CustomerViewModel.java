package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CustomerViewModel extends AndroidViewModel {
    CustomerRepo customerRepo;
    private LiveData<List<CustomerEntity>> AllCustomers;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepo=new CustomerRepo(application);
        AllCustomers=customerRepo.getAllCustomers();
    }
    public void insert(CustomerEntity customerEntity){
        customerRepo.insert(customerEntity);
    }
    public void update(CustomerEntity customerEntity){
        customerRepo.update(customerEntity);
    }
    public void delete(CustomerEntity customerEntity){
        customerRepo.delete(customerEntity);
    }

    public LiveData<List<CustomerEntity>> getAllCustomers(){
        return AllCustomers;
    }


}
