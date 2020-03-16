package com.example.dedan.digitalreceipts.Database.Store;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class CategoryRepo {
    private LiveData<List<CategoryEntity>> allCategory;
    private CategoryEntity category;
    private CategoryDao dao;

    public CategoryRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        dao =appDatabase.categoryDao();
        allCategory = dao.getAllCategories();
    }
    public void insert(CategoryEntity categoryEntity){
        new insertAsyncTask(dao).execute(categoryEntity);
    }
    public void update(CategoryEntity categoryEntity){
        new updateAsyncTask(dao).execute(categoryEntity);
    }
    public  void delete(CategoryEntity categoryEntity){
        new deleteAsyncTask(dao).execute(categoryEntity);
    }
    public LiveData<List<CategoryEntity>> AllCategories(){
        return allCategory;
    }
    public void deleteAll(){
        new deleteAllAsync(dao).execute();
    }

    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private CategoryDao dao;
        public deleteAllAsync(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<CategoryEntity,Void,Void> {
        private CategoryDao dao;

        private deleteAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            dao.delete(categoryEntities[0]);
            return null;
        }
    }
        private static class insertAsyncTask extends AsyncTask<CategoryEntity,Void,Void> {
        private CategoryDao dao;

        public insertAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            dao.insert(categoryEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<CategoryEntity,Void,Void>{
        private CategoryDao dao;

        public updateAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            dao.update(categoryEntities[0]);
            return null;
        }
    }
}
