package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CustomerDao {
    @Insert
    void insert(CustomerEntity customerEntity);

    @Update
    void update(CustomerEntity customerEntity);

    @Delete
    void delete(CustomerEntity customerEntity);

    @Query("select * from Customers order by first_name desc")
    LiveData<List<CustomerEntity>> getAllCustomers();
}
