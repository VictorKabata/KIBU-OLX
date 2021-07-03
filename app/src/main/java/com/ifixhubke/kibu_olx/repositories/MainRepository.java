package com.ifixhubke.kibu_olx.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ifixhubke.kibu_olx.models.Products;
import com.ifixhubke.kibu_olx.data.ItemsDao;
import com.ifixhubke.kibu_olx.data.ItemsDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    private final ItemsDao itemsDao;
    private final LiveData<List<Products>> allItems;
    MutableLiveData<ArrayList<Products>> itemsList = new MutableLiveData<>();

    //var fragment2EditText: String? = null
    public MainRepository(Application application) {
        ItemsDatabase database = ItemsDatabase.getInstance(application);
        itemsDao = database.itemsDao();
        allItems = itemsDao.getAllItems();
    }

    public void insert(Products products) {
        new InsertItemAsyncTask(itemsDao).execute(products);
    }

    public void delete(Products products) {
        new DeleteItemAsyncTask(itemsDao).execute(products);
    }

    public LiveData<List<Products>> getAllItems() {
        return allItems;
    }

    public void updateSoldItem(Boolean isSoldOut, int id) {
        new UpdateItemSoldOut(itemsDao, isSoldOut, id).execute();
    }


    //AsyncTasks to execute the code in the background thread because database operations should not be executed in the UI thread

    //Insert a item
    private static class InsertItemAsyncTask extends AsyncTask<Products, Void, Void> {

        private final ItemsDao itemsDao;

        private InsertItemAsyncTask(ItemsDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Products... products) {

            itemsDao.insert(products[0]);
            return null;
        }
    }

    private static class UpdateItemSoldOut extends AsyncTask<Boolean, Void, Void> {

        int id;
        Boolean isSoldOut;
        private final ItemsDao itemsDao;

        private UpdateItemSoldOut(ItemsDao itemsDao, Boolean isSoldOut, int id) {
            this.itemsDao = itemsDao;
            this.id = id;
            this.isSoldOut = isSoldOut;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            itemsDao.updateItemSoldOut(isSoldOut, id);
            return null;
        }
    }

    //Delete a item
    private static class DeleteItemAsyncTask extends AsyncTask<Products, Void, Void> {

        private final ItemsDao itemsDao;

        private DeleteItemAsyncTask(ItemsDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Products... products) {

            itemsDao.delete(products[0]);
            return null;
        }
    }
}
