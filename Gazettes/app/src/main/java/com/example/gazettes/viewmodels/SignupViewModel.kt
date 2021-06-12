package com.example.gazettes.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class SignupViewModel: ViewModel() {
    var email:String? = null
    var name:String? = null
    var pass:String? = null
    var cpass:String? = null
    var age:String? = null
    var phoneno:String? = null
    var address:String? = null
    var bio:String? = null

    lateinit var signupListener:Signuplistener


    fun onRegisterButtonClick(view: View){



        signupListener?.on_Started()
        if(email.isNullOrEmpty() || pass.isNullOrEmpty() ||name.isNullOrEmpty()|| cpass.isNullOrEmpty() || age.isNullOrEmpty() || phoneno.isNullOrEmpty() || address.isNullOrEmpty() || bio.isNullOrEmpty()){
            signupListener?.on_Failure("Invalid Details")
            return
        }

        signupListener?.on_Success()

    }
}