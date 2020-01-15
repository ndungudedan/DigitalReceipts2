package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PickedGoodRepo {

    private PickedGoodDao pickedGoodDao;
    private LiveData<List<PickedGoodEntity>> allLivePickedGoods;
    private List<PickedGoodEntity> allPickedGoodsList;
    private LiveData<List<PickedGoodEntity>> total;
    int updateRes;

    public PickedGoodRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        pickedGoodDao=appDatabase.pickedGoodDao();
        allLivePickedGoods =pickedGoodDao.getAllLivePickedGoods();
    }
    public void insert(PickedGoodEntity pickedGoodEntity){
        new insertPickedGoodsAsyncTask(pickedGoodDao).execute(pickedGoodEntity);
    }
    public int update(PickedGoodEntity pickedGoodEntity){
        updatePickedGoodsAsyncTask task=new updatePickedGoodsAsyncTask(pickedGoodDao);
        task.repo=this;
        task.execute(pickedGoodEntity);
        return updateRes;
    }

    public void delete(PickedGoodEntity pickedGoodEntity){
        new deletePickedGoodAsyncTask(pickedGoodDao).execute(pickedGoodEntity);
    }
    public LiveData<List<PickedGoodEntity>> getAllPickedGoods(){
        return allLivePickedGoods;
    }

    public List<PickedGoodEntity> getAllPickedGoodsList(){
        allPickedGoodsListAsync task=new allPickedGoodsListAsync(pickedGoodDao);
        task.repo=this;
        task.execute();
        return allPickedGoodsList;
    }
    public void deleteAll(){
        new deleteAllAsync(pickedGoodDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private PickedGoodDao dao;
        public deleteAllAsync(PickedGoodDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class allPickedGoodsListAsync extends AsyncTask<Void,Void,List<PickedGoodEntity>>{
        private PickedGoodDao pickedGoodDao;
        private PickedGoodRepo repo=null;

        public allPickedGoodsListAsync(PickedGoodDao pickedGoodDao) {
            this.pickedGoodDao = pickedGoodDao;
        }

        @Override
        protected List<PickedGoodEntity> doInBackground(Void... voids) {
            return pickedGoodDao.getPickedGoodsList();
        }

        @Override
        protected void onPostExecute(List<PickedGoodEntity> pickedGoodEntities) {
            super.onPostExecute(pickedGoodEntities);
            repo.allPickedGoodsList=pickedGoodEntities;
        }
    }

    private static class insertPickedGoodsAsyncTask extends AsyncTask<PickedGoodEntity,Void, Void>{
        private PickedGoodDao pickedGoodDao;
        public insertPickedGoodsAsyncTask(PickedGoodDao pickedGoodDao) {
            this.pickedGoodDao = pickedGoodDao;
        }
        @Override
        protected Void doInBackground(PickedGoodEntity... pickedGoodEntities) {
            pickedGoodDao.insert(pickedGoodEntities[0]);
            return null;
        }
    }
    private static class updatePickedGoodsAsyncTask extends AsyncTask<PickedGoodEntity,Void,Integer>{
        private PickedGoodDao pickedGoodDao;
        private PickedGoodRepo repo=null;
        int res;
        public updatePickedGoodsAsyncTask(PickedGoodDao pickedGoodDao) {
            this.pickedGoodDao = pickedGoodDao;
        }
        @Override
        protected Integer doInBackground(PickedGoodEntity... pickedGoodEntities) {
            int res=pickedGoodDao.update(pickedGoodEntities[0]);
            return res;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            repo.updateRes=integer;
        }
    }

    private static class deletePickedGoodAsyncTask extends AsyncTask<PickedGoodEntity,Void,Void>{
        private PickedGoodDao pickedGoodDao;
        public deletePickedGoodAsyncTask(PickedGoodDao pickedGoodDao){
            this.pickedGoodDao=pickedGoodDao;
        }
        @Override
        protected Void doInBackground(PickedGoodEntity... pickedGoodEntities) {
            pickedGoodDao.delete(pickedGoodEntities[0]);
            return null;
        }
    }
}
