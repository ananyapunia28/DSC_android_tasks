package com.example.gazettes.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class SignViewModel: ViewModel() {
    var email:String? = null
    var pass:String? = null
    var cpass:String? = null
    var age:String? = null
    var phoneno:String? = null
    var address:String? = null
    var bio:String? = null

    lateinit var signupListener: SignupListener


    fun onSignupBtnClick(view: View) {
        signupListener.on_Started()
//        if (email.isNullOrEmpty() || pass.isNullOrEmpty() || cpass.isNullOrEmpty() || age.isNullOrEmpty() || phoneno.isNullOrEmpty() || address.isNullOrEmpty() || bio.isNullOrEmpty()) {
//            signupListener?.onFailure("Invalid Details")
//            return
//        }
//        signupListener?.onSuccess()
    }
}