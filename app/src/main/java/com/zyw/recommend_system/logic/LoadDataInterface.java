package com.zyw.recommend_system.logic;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
//获取数据接口
public interface LoadDataInterface<T> {
    ListenableFuture<List<T>> load(int pageNum);
}
