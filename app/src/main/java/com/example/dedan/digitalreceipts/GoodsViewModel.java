package com.example.dedan.digitalreceipts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class GoodsViewModel extends AndroidViewModel {
    private GoodsRepository goodsRepository;
    private LiveData<List<GoodsEntity>> allGoods;
    private List<GoodsEntity> getItems;
    private List<GoodsEntity> getPack;
    private List<GoodsEntity> getCost;

    public GoodsViewModel(@NonNull Application application) {
        super(application);
        goodsRepository=new GoodsRepository(application);
        allGoods=goodsRepository.getAllGoods();
        getItems=goodsRepository.getItems();
        getPack=goodsRepository.getPack();
        getCost=goodsRepository.getCost();
    }
    public void insert(GoodsEntity goodsEntity){
        goodsRepository.insert(goodsEntity);
    }
    public void update(GoodsEntity goodsEntity){
        goodsRepository.update(goodsEntity);
    }
    public void delete(GoodsEntity goodsEntity){
        goodsRepository.delete(goodsEntity);
    }
    public LiveData<List<GoodsEntity>> getAllGoods(){
        return goodsRepository.getAllGoods();
    }
    public List<GoodsEntity> getItems(){
        return goodsRepository.getItems();
    }
    public List<GoodsEntity> getPack(){
        return goodsRepository.getPack();
    }
    public List<GoodsEntity> getCost(){
        return goodsRepository.getCost();
    }
}
