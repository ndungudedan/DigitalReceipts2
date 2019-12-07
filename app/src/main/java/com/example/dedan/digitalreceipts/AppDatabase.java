package com.example.dedan.digitalreceipts;

import android.content.Context;

import com.example.dedan.digitalreceipts.Month_Database.April.AprDao;
import com.example.dedan.digitalreceipts.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Month_Database.August.AugDao;
import com.example.dedan.digitalreceipts.Month_Database.August.AugEntity;
import com.example.dedan.digitalreceipts.Month_Database.December.DecDao;
import com.example.dedan.digitalreceipts.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Month_Database.February.FebDao;
import com.example.dedan.digitalreceipts.Month_Database.February.FebEntity;
import com.example.dedan.digitalreceipts.Month_Database.January.JanDao;
import com.example.dedan.digitalreceipts.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Month_Database.July.JulDao;
import com.example.dedan.digitalreceipts.Month_Database.July.JulEntity;
import com.example.dedan.digitalreceipts.Month_Database.June.JunDao;
import com.example.dedan.digitalreceipts.Month_Database.June.JunEntity;
import com.example.dedan.digitalreceipts.Month_Database.March.MarDao;
import com.example.dedan.digitalreceipts.Month_Database.March.MarEntity;
import com.example.dedan.digitalreceipts.Month_Database.May.MayDao;
import com.example.dedan.digitalreceipts.Month_Database.May.MayEntity;
import com.example.dedan.digitalreceipts.Month_Database.November.NovDao;
import com.example.dedan.digitalreceipts.Month_Database.November.NovEntity;
import com.example.dedan.digitalreceipts.Month_Database.October.OctDao;
import com.example.dedan.digitalreceipts.Month_Database.October.OctEntity;
import com.example.dedan.digitalreceipts.Month_Database.September.SepDao;
import com.example.dedan.digitalreceipts.Month_Database.September.SepEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserEntity.class,GoodsEntity.class,PickedGoodEntity.class,MonthSalesEntity.class, WeekSalesEntity.class, ReceiptEntity.class,
        CustomerEntity.class,UserStatsEntity.class, JanEntity.class, FebEntity.class, MarEntity.class, AprEntity.class, MayEntity.class, JunEntity.class,
        JulEntity.class, AugEntity.class, SepEntity.class, OctEntity.class, NovEntity.class, DecEntity.class},version = 13)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase dbInstance;
    public abstract UserDao userDao();
    public abstract GoodsDao goodsDao();
    public abstract MonthSalesDao monthSalesDao();
    public abstract WeekSalesDao weekSalesDao();
    public abstract ReceiptDao dailySalesDao();
    public abstract PickedGoodDao pickedGoodDao();
    public abstract CustomerDao customerDao();
    public abstract UserStatsDao userStatsDao();
    public abstract JanDao janDao();public abstract FebDao febDao();public abstract MarDao marDao();
    public abstract AprDao aprDao();public abstract MayDao mayDao();public abstract JunDao junDao();
    public abstract JulDao julDao();public abstract AugDao augDao();public abstract SepDao sepDao();
    public abstract OctDao octDao();public abstract NovDao novDao();public abstract DecDao decDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "App_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return dbInstance;
    }
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
    }
