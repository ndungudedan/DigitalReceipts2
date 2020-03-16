package com.example.dedan.digitalreceipts.Database.Store;

import android.app.Application;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepo categoryRepo;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepo=new CategoryRepo(application);
    }
    public void insert(CategoryEntity categoryEntity){
        categoryRepo.insert(categoryEntity);
    }
    public void update(CategoryEntity categoryEntity){
        categoryRepo.update(categoryEntity);
    }
    public LiveData<List<CategoryEntity>> AllCategorys(){
        return categoryRepo.AllCategories();
    }
    public void delete(CategoryEntity categoryEntity){
        categoryRepo.delete(categoryEntity);
    }

    public void deleteAll(){
        categoryRepo.deleteAll();
    }
}
