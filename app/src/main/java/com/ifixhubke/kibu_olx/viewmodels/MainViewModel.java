package com.ifixhubke.kibu_olx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ifixhubke.kibu_olx.models.Products;
import com.ifixhubke.kibu_olx.network.FirebaseSource;
import com.ifixhubke.kibu_olx.repositories.HomeRepository;
import com.ifixhubke.kibu_olx.repositories.MainRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final HomeRepository homeRepository;
    private final LiveData<List<Products>> allItems;
    public LiveData<Boolean> timeout;
    public LiveData<Boolean> connectedToInternet;
    public MutableLiveData<ArrayList<Products>> itemsList = new MutableLiveData<>();

    @Inject
    FirebaseSource firebaseSource;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        homeRepository = new HomeRepository(firebaseSource);
        allItems = mainRepository.getAllItems();
    }


    /**
     * wrapper methods for database operations, from the repository
     **/
    public void insert(Products products) {
        mainRepository.insert(products);
    }

    public void delete(Products products) {
        mainRepository.delete(products);
    }

    public LiveData<List<Products>> allItems() {
        return allItems;
    }

    public void updateSoldItem(Boolean isSoldOut, int id) {
        mainRepository.updateSoldItem(isSoldOut, id);
    }

    /*public LiveData<ArrayList<Products>> fetchItems() {
        return homeRepository.fetchItems();
    }

    public void refresh() {
        homeRepository.refresh();
    }*/
}
