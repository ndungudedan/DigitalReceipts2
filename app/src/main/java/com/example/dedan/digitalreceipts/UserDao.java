package com.example.dedan.digitalreceipts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

    @Query("select * from Users")
     LiveData<List<UserEntity>> getAllUsers();

    @Query("select * from Users where password = :pass and email = :user limit 1")
    UserEntity getUser(String pass,String user);

    /*@Query("select email username from Users where email = :emailaddress and username = :user limit 1")
    UserEntity checkUserExists(String emailaddress,String user);*/
}
