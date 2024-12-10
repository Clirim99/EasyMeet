package com.example.scheduleyourweek.utility;

import android.content.Context;
import android.util.Log;

import com.example.scheduleyourweek.model.User;
import com.example.scheduleyourweek.repository.UserRepository;

public class Authentication {


    public static Boolean logIn(Context context, String email, String password){
        User user = UserRepository.loginUser(context, email, password);
        Log.d("User " , String.valueOf(user.getId()));
        SessionManager.saveUserId(context,user.getId());
        return user != null;
    }

    public static Boolean signUp(Context context, User user){
        if (UserRepository.isEmailTaken(context,user.getEmail())){
            return false;
        }
        else {
        return UserRepository.insertUser(context, user);
        }

    }

}
