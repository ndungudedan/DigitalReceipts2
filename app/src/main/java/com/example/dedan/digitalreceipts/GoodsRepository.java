package com.example.dedan.digitalreceipts;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import java.util.List;

public class GoodsRepository {
    private GoodsDao goodsDao;
    private LiveData<List<GoodsEntity>> allGoods;

    public GoodsRepository(Application application){
        AppDatabase database=AppDatabase.getInstance(application);
        goodsDao=database.goodsDao();
        allGoods=goodsDao.getAllGoods();

    }

    public void insert(GoodsEntity goodsEntity){
        new insertGoodsAsyncTask(goodsDao).execute(goodsEntity);

    }
    public void update(GoodsEntity goodsEntity){
        new updateGoodsAsyncTask(goodsDao).execute(goodsEntity);
    }
    public void delete(GoodsEntity goodsEntity){
        new deleteGoodsAsyncTask(goodsDao).execute(goodsEntity);
    }
    public LiveData<List<GoodsEntity>> getAllGoods(){
        return allGoods;
    }

    public void deleteAll(){
        new deleteAllAsync(goodsDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private GoodsDao dao;
        public deleteAllAsync(GoodsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    public static class insertGoodsAsyncTask extends AsyncTask<GoodsEntity,Void,Void>{
        private GoodsDao goodsDao;
        private insertGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao=goodsDao;
        }
        @Override
        protected Void doInBackground(GoodsEntity... goodsEntities) {
            goodsDao.insert(goodsEntities[0]);
            return null;
        }
    }
    public static class updateGoodsAsyncTask extends AsyncTask<GoodsEntity,Void,Void>{
        private GoodsDao goodsDao;
        private updateGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao=goodsDao;
        }
        @Override
        protected Void doInBackground(GoodsEntity... goodsEntities) {
            goodsDao.update(goodsEntities[0]);
            return null;
        }
    }
    public static class deleteGoodsAsyncTask extends AsyncTask<GoodsEntity,Void,Void>{
        private GoodsDao goodsDao;
        private deleteGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao=goodsDao;
        }
        @Override
        protected Void doInBackground(GoodsEntity... goodsEntities) {
            goodsDao.delete(goodsEntities[0]);
            return null;
        }
    }
}
