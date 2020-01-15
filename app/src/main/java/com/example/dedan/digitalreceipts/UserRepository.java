package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserEntity>> allUsers;
    public UserEntity getUser;

    public UserRepository(Application application){
        AppDatabase database= AppDatabase.getInstance(application);
        userDao=database.userDao();
        allUsers=userDao.getAllUsers();
    }
    public void insert(UserEntity userEntity){
        new InsertUserAsyncTask(userDao).execute(userEntity);
    }
    public void update(UserEntity userEntity){
        new UpdateUserAsyncTask(userDao).execute(userEntity);
    }
    public void delete(UserEntity userEntity){

    }
    public LiveData<List<UserEntity>> getAllUsers(){
    return allUsers;
    }

    public UserEntity getSingleUser(String user,String pass){
        getSingleUserAsyncTask task= new getSingleUserAsyncTask(userDao,pass,user);
        task.repo=this;
        try {
            getUser=task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getUser;
    }
    private static class getSingleUserAsyncTask extends AsyncTask<Void,Void,UserEntity> {
        private UserRepository repo=null;
        private String AsyncPas,AsyncUser;
        private UserDao userDao;
        private getSingleUserAsyncTask(UserDao userDao,String AsyncPas,String AsyncUser){
            this.userDao=userDao;
            this.AsyncUser=AsyncUser;
            this.AsyncPas=AsyncPas;
        }
        @Override
        protected UserEntity doInBackground(Void... voids) {
            return userDao.getUser(AsyncPas,AsyncUser);
        }

        @Override
        protected void onPostExecute(UserEntity userEntity) {
            super.onPostExecute(userEntity);
            repo.getUser=userEntity;
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserEntity,Void,Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... userEntities) {
            userDao.insert(userEntities[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<UserEntity,Void,Void> {
        private UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... userEntities) {
            userDao.update(userEntities[0]);
            return null;
        }
    }


}
