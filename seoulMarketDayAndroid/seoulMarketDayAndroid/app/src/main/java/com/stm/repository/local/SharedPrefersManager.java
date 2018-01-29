package com.stm.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.stm.common.dao.User;


/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public class SharedPrefersManager {
    private Context context;

    private SharedPreferences sharedUserPreferences;
    private SharedPreferences sharedNotificationPreferences;

    public SharedPrefersManager(Context context) {
        this.context = context;

        if (sharedUserPreferences == null) {
            sharedUserPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        }

        if(sharedNotificationPreferences == null){
            sharedNotificationPreferences = context.getSharedPreferences("notification", Context.MODE_PRIVATE);
        }
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = sharedUserPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = sharedUserPreferences.edit();

        Gson gson = new Gson();
        String stringUser = gson.toJson(user);
        editor.putString("user", stringUser).apply();
        editor.commit();
    }

    public void removeUser(){
        SharedPreferences.Editor editor = sharedUserPreferences.edit();
        editor.remove("user").apply();
        editor.commit();
    }

    public boolean isAllowedForNotification(){
        return  sharedNotificationPreferences.getBoolean("isAllowed", true);
    }

    public void setAllowedForNotification(boolean isAllowed){
        SharedPreferences.Editor editor = sharedNotificationPreferences.edit();
        editor.putBoolean("isAllowed", isAllowed);
        editor.apply();
    }
}
