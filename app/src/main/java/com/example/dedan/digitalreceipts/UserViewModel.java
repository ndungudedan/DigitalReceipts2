package com.example.dedan.digitalreceipts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<UserEntity>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository=new UserRepository(application);
        allUsers=userRepository.getAllUsers();
    }

    public void insert(UserEntity userEntity){
        userRepository.insert(userEntity);
    }
    public void delete(UserEntity userEntity){
        userRepository.delete(userEntity);
    }
    public void update(UserEntity userEntity){
        userRepository.update(userEntity);
    }
    public LiveData<List<UserEntity>> getAllUsers(){
        return allUsers;
    }

    public UserEntity getSingleUser(String user,String pass){
        return userRepository.getSingleUser(user,pass);
    }
}
