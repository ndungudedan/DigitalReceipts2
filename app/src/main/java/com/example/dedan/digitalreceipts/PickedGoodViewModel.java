package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PickedGoodViewModel extends AndroidViewModel {
    private PickedGoodRepo pickedGoodRepo;
    private LiveData<List<PickedGoodEntity>> allLivePickedGoods;
    private List<PickedGoodEntity> allPickedGoodsList;
    private LiveData<List<PickedGoodEntity>> total;

    public PickedGoodViewModel(@NonNull Application application) {
        super(application);
        pickedGoodRepo= new PickedGoodRepo(application);
        allLivePickedGoods=pickedGoodRepo.getAllPickedGoods();
        allPickedGoodsList=pickedGoodRepo.getAllPickedGoodsList();
    }

    public void insert(PickedGoodEntity pickedGoodEntity){
        pickedGoodRepo.insert(pickedGoodEntity);
    }
    public int update(PickedGoodEntity pickedGoodEntity){
        int x=pickedGoodRepo.update(pickedGoodEntity);
        return x;
    }
    public void delete(PickedGoodEntity pickedGoodEntity){
        pickedGoodRepo.delete(pickedGoodEntity);
    }
    public LiveData<List<PickedGoodEntity>> getAllPickedGoods(){
        return allLivePickedGoods;
    }

    public List<PickedGoodEntity> getAllPickedGoodsList(){
        return allPickedGoodsList;
    }

}
