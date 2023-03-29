package com.zyw.recommend_system.ui.real_name;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.model.RealName;

public class RealNameViewModel extends ViewModel {
    public LiveData<RealName> postRealNameState(String name, String number){
        return Repository.getInstance().postRealName(name, number);
    }

    public void setRealNameState(Boolean realNameState) {
        Repository.getInstance().setRealName(realNameState);
    }

    public void setNewToken(String token) {
        Repository.getInstance().setToken(token);
    }
}
