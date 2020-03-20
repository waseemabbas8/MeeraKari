package com.example.tahreem_fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String VENDOR = "VENDOR";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email) {

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((login) context).finish();

    }

    public void createSessionVendor(String name, String email,String VENDOR) {

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
      //  editor.putBoolean(VENDOR,v)
        editor.apply();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((login) context).finish();

    }


//    public void createSessionScholar(String name, String email) {
//
//        editor.putBoolean(LOGIN, true);
//        editor.putString(NAME, name);
//        editor.putString(EMAIL, email);
//        editor.apply();
//        Intent i = new Intent(context, MainActivity.class);
//        context.startActivity(i);
//        ((SignupScholar) context).finish();
//
//    }

    public boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {

        if (!this.isLoggin()) {
            Intent i = new Intent(context, login.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return user;
    }

    public void logout() {

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, login.class);//signin
        context.startActivity(i);
        ((MainActivity) context).finish();

    }
}
