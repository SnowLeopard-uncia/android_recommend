package com.zyw.recommend_system.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.zyw.recommend_system.MyApplication;
import com.zyw.recommend_system.UserInfo;


public class UserDao {


//   public static void saveUser(UserInfo userInfo){
//        sharePreference().edit()
//                .putString("userName",userInfo.getUsername())
//                .putString("userHead",userInfo.getProPath())
//                .apply();
////        sharePreference().edit().commit();
//
//   }
//
//   public static UserInfo getUser(){
//       String userName = sharePreference().getString("userName", "hello");
//       String uesrHead = sharePreference().getString("userHead","");
//       return new UserInfo(uesrHead,userName);
//   }

    public static boolean getFirst(){
        return sharePreference().getBoolean("first", true);
    }

   public static void saveFirst(boolean isFirst){
        sharePreference().edit()
                .putBoolean("first",false)
                .apply();
   }

    public static String getGUID(){
        return sharePreference().getString("GUID", "");
    }

    public static void saveGUID(String GUID){
        sharePreference().edit()
                .putString("GUID",GUID)
                .apply();
    }
    public static String getToken(){
        return sharePreference().getString("token", "");
    }

    public static void saveToken(String token){
        sharePreference().edit()
                .putString("token",token)
                .apply();
    }

    public static String getUserId(){
        return sharePreference().getString("userId", "");
    }

    public static void saveUserId(String userId){
        sharePreference().edit()
                .putString("userId",userId)
                .apply();
    }

    public static boolean getRealName(){
        return sharePreference().getBoolean("RealName", false);
    }

    public static void setRealName(boolean isReal){
        sharePreference().edit()
                .putBoolean("RealName",true)
                .apply();
    }
   public static  SharedPreferences sharePreference(){
       return MyApplication.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
   }
}
