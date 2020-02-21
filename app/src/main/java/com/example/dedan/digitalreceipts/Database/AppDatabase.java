package com.example.dedan.digitalreceipts.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.CustomerDao;
import com.example.dedan.digitalreceipts.CustomerEntity;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthDao;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthEntity;
import com.example.dedan.digitalreceipts.GoodsDao;
import com.example.dedan.digitalreceipts.GoodsEntity;
import com.example.dedan.digitalreceipts.MonthSalesDao;
import com.example.dedan.digitalreceipts.MonthSalesEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepEntity;
import com.example.dedan.digitalreceipts.PickedGoodDao;
import com.example.dedan.digitalreceipts.PickedGoodEntity;
import com.example.dedan.digitalreceipts.ReceiptDao;
import com.example.dedan.digitalreceipts.ReceiptEntity;
import com.example.dedan.digitalreceipts.UserDao;
import com.example.dedan.digitalreceipts.UserEntity;
import com.example.dedan.digitalreceipts.UserStatsDao;
import com.example.dedan.digitalreceipts.UserStatsEntity;
import com.example.dedan.digitalreceipts.WeekSalesDao;
import com.example.dedan.digitalreceipts.WeekSalesEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserEntity.class, GoodsEntity.class, PickedGoodEntity.class, MonthSalesEntity.class, WeekSalesEntity.class, ReceiptEntity.class,
        CustomerEntity.class, UserStatsEntity.class, JanEntity.class, FebEntity.class, MarEntity.class, AprEntity.class, MayEntity.class, JunEntity.class,
        JulEntity.class, AugEntity.class, SepEntity.class, OctEntity.class, NovEntity.class, DecEntity.class, MonEntity.class,
        TueEntity.class, WedEntity.class, ThurEntity.class, FriEntity.class, SatEntity.class, SunEntity.class, UserStatsMonthEntity.class},version = 11)
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
    public abstract MonDao monDao();public abstract TueDao tueDao();public abstract WedDao wedDao();
    public abstract ThurDao thurDao();public abstract FriDao friDao();public abstract SatDao satDao();public abstract SunDao sunDao();
    public abstract UserStatsMonthDao todayDao();
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
            new insertasync(dbInstance).execute();
            super.onCreate(db);
        }
    };
    private static class insertasync extends AsyncTask<Void,Void,Void> {
        private CustomerDao customerDao;

        private insertasync(AppDatabase db) {
            customerDao=db.customerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            customerDao.insert(new CustomerEntity("NAME","SURNAME","N/A",
                    "N/A","N/A","N/A","N/A"));
            return null;
        }
    }
    }
