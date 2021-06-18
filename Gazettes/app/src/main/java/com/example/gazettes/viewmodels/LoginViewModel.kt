package com.example.gazettes.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    var email:String? = null
    var password:String? = null
//    lateinit var loginListener: LoginListener


    lateinit var authListener: LoginListener



    fun onloginBtnClick(view: View){

        authListener?.onStarted()


    }

}