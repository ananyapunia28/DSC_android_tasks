package com.example.gazettes.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){
    var email:String? = null
    var password:String? = null


    lateinit var loginListener: LoginListener



    fun onLoginButtonClick(view: View){

        loginListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            loginListener?.onFailure("Invalid")
            return
        }

        loginListener?.onSuccess()

    }
}

