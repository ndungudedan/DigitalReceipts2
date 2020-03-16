package com.example.dedan.digitalreceipts;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoodsRepository {
    private GoodsDao goodsDao;
    private LiveData<List<GoodsEntity>> allGoods;
    private LiveData<List<GoodsEntity>> goods;

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
    public void deleteCategoryGoods(String cat){
        new deleteCategoryAsync(goodsDao).execute(cat);
    }
    public LiveData<List<GoodsEntity>> getAllGoods(){
        return allGoods;
    }

    public LiveData<List<GoodsEntity>> getCategoryGoods(String category){
        GoodsCatAsync task=new GoodsCatAsync(goodsDao);
        task.Repo=this;
        try {
            goods=task.execute(category).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return goods;
    }
    private static class GoodsCatAsync extends AsyncTask<String,Void, LiveData<List<GoodsEntity>>> {
        private GoodsDao dao;
        private GoodsRepository Repo=null;

        public GoodsCatAsync(GoodsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<GoodsEntity>> doInBackground(String... strings) {
            return dao.getCategoryGoods(strings[0]);
        }
    }
    public void deleteAll(){
        new deleteAllAsync(goodsDao).execute();
    }
    private static class deleteCategoryAsync extends AsyncTask<String,Void,Void>{
        private GoodsDao dao;
        public deleteCategoryAsync(GoodsDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            dao.deleteCategoryGoods(strings[0]);
            return null;
        }
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
