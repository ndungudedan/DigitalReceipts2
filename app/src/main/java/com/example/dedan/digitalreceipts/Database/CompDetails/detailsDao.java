package com.example.dedan.digitalreceipts.Database.CompDetails;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface detailsDao extends BaseDao<detailsEntity> {
    @Query("select * from Company_Details")
    LiveData<detailsEntity> companyDetails();

}
