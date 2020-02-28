package com.example.dedan.digitalreceipts.Database.Store;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "Category")
    private String KEY_Category;

    public CategoryEntity(String KEY_Category) {
        this.KEY_Category = KEY_Category;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_Category() {
        return KEY_Category;
    }

    public void setKEY_Category(String KEY_Category) {
        this.KEY_Category = KEY_Category;
    }
}
