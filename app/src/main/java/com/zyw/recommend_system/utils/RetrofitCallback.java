package com.zyw.recommend_system.utils;

import android.widget.Toast;


import com.zyw.recommend_system.MyApplication;
import com.zyw.recommend_system.logic.dao.UserDao;
import com.zyw.recommend_system.logic.network.ArticleNetWork;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * 一个普通类实现接口的时候，要实现这个接口的所有方法，但是如果是抽象类的话就可以不用实现这个接口的所有方法
 * https://blog.csdn.net/GoodLi199309/article/details/79947756
 * 此类是专门用于处理onFailure 统一处理异常
 * @param <T>
 */
public abstract class RetrofitCallback<T> implements Callback<T> {

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof ApiException){
            ApiException apiException = (ApiException) t;
            if (apiException.isFail()){
                t.printStackTrace();//相应处理
                Toast.makeText(MyApplication.getContext(),apiException.getMsg(),Toast.LENGTH_SHORT).show();
            }else if (apiException.isAccessErrorReg()){
                ArticleNetWork.userLogin(UserDao.getGUID());
            }

        }
        t.printStackTrace();
    }
}
