package com.example.easymeet.utility;

import android.content.Context;

import com.example.easymeet.model.User;
import com.example.easymeet.repository.UserRepository;

public class Authentication {


    public static Boolean logIn(Context context, String email, String password){
        return UserRepository.loginUser(context, email, password) != null;
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
