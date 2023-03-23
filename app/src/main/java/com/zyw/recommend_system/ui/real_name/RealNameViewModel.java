package com.zyw.recommend_system.ui.real_name;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zyw.recommend_system.logic.Repository;

public class RealNameViewModel extends ViewModel {
    public LiveData<Boolean> postRealNameState(String name, String number){
        return Repository.getInstance().postRealName(name, number);
    }

    public void setRealNameState(Boolean realNameState) {
        Repository.getInstance().setRealName(realNameState);
    }
}
