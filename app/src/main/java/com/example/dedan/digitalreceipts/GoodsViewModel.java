package com.example.dedan.digitalreceipts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class GoodsViewModel extends AndroidViewModel {
    private GoodsRepository goodsRepository;
    private LiveData<List<GoodsEntity>> allGoods;

    public GoodsViewModel(@NonNull Application application) {
        super(application);
        goodsRepository=new GoodsRepository(application);
        allGoods=goodsRepository.getAllGoods();
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
        return allGoods;
    }
    public void deleteAll(){
        goodsRepository.deleteAll();
    }
    public LiveData<List<GoodsEntity>> getAllCategoryGoods(String category){
        return goodsRepository.getCategoryGoods(category);
    }
    public void deleteCategoryGoods(String cat){
        goodsRepository.deleteCategoryGoods(cat);
    }

}
