package com.example.gazettes.UI


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gazettes.R
import com.example.gazettes.databinding.FragmentLoginBinding
import com.example.gazettes.viewmodels.LoginListener
import com.example.gazettes.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.regex.Pattern

class LoginFragment : Fragment(R.layout.fragment_login),LoginListener {
    lateinit var binding: FragmentLoginBinding

    lateinit var navcontroller:NavController


    lateinit var firebaseAuth: FirebaseAuth

    lateinit var progressDialog: ProgressDialog
    private var email =""
    private var password =""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        val viewModel= ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.logindetails= viewModel
        viewModel.authListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navcontroller = Navigation.findNavController(view)
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in")
        progressDialog.setCanceledOnTouchOutside(false)
        navcontroller = Navigation.findNavController(view)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            navcontroller.navigate(R.id.action_loginFragment2_to_mainFragment)
        } else {
            binding.loginBtn.setOnClickListener() {
                progressDialog.show()


                closeKeyboard()
                binding.loginBtn.visibility = Button.VISIBLE
                val email: String = binding.emailEt.text.toString().trim()
                val password: String = binding.pswrdEt.text.toString().trim()

                if (checkemail(email) && validatepass(password))
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->


                        if (task.isSuccessful) {

                            navcontroller.navigate(R.id.action_loginFragment2_to_mainFragment)
                            progressDialog.dismiss()
                        } else {

                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
                else{
                    Toast.makeText(context, "Invalid!!", Toast.LENGTH_SHORT).show()
                    binding.loginBtn.visibility = Button.VISIBLE
                    progressDialog.dismiss()
                }
            }
        }


        binding.noAccountTv.setOnClickListener {

            navcontroller.navigate(R.id.signupFragment)
        }


//        binding.loginBtn.setOnClickListener{

//            checkUser()
//            firebaseLogin()
//        }


    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }


//    override fun onStarted() {
//
//        email = binding.emailEt.text.toString().trim()
//        password = binding.pswrdEt.text.toString().trim()
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//
//            binding.emailEt.error="Invalid Email"
//        }
//        else if(TextUtils.isEmpty(password)){
//
//            binding.pswrdEt.error="Enter password"
//
//        }
//        else if(!validatepass(password)){
//            Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
//        }
//        else
//        {
//
//        }
//    }

    override fun onSuccess() {

        findNavController().popBackStack()
    }

    override fun onFailure(message: String) {


    }

//    private fun checkUser() {
//
//
//        if(firebaseAuth.currentUser !=null){
//
//            navcontroller.navigate(R.id.action_loginFragment2_to_mainFragment)
//        }
//        else {
//
//            firebaseLogin()
//        }
//    }
//
//    private fun firebaseLogin() {
//
//        progressDialog.show()
//
//            if (checkemail(email) && validatepass(password))
//                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
//
//                    progressDialog.dismiss()
////            val firebaseUser = firebaseAuth.currentUser
////            val email =firebaseUser!!.email
//                    view?.let {
//                        Snackbar.make(it, "Logged In As ${email}", Snackbar.LENGTH_SHORT).show()
//                    }
//                    navcontroller.navigate(R.id.action_loginFragment2_to_mainFragment)
//
//
//                }
//
//
//            firebaseAuth.signInWithEmailAndPassword(email, password).addOnFailureListener { e ->
//                progressDialog.dismiss()
//                Toast.makeText(context, "Login Failed, Sign-up", Toast.LENGTH_SHORT).show()
//                navcontroller.navigate(R.id.signupFragment)
//            }
//
//    }

    fun checkemail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
    fun validatepass(password: String): Boolean {
        var regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,12}$"
        var p: Pattern = Pattern.compile(regex)
        if (password.isEmpty()) return false
        var m = p.matcher(password)
        return m.matches()
    }
    private fun closeKeyboard() {
        val inputManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


}