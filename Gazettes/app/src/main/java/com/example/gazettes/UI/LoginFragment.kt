package com.example.gazettes.UI


import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gazettes.R
import com.example.gazettes.databinding.FragmentLoginBinding
import com.example.gazettes.viewmodels.LoginListener
import com.example.gazettes.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login), LoginListener{
    lateinit var binding: FragmentLoginBinding

    lateinit var navcontroller:NavController


    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog
    private var email =""
    private var password =""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val viewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.logindetails = LoginViewModel()
        viewModel.loginListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navcontroller = Navigation.findNavController(view)


        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth = FirebaseAuth.getInstance()



        binding.noAccountTv.setOnClickListener {
            navcontroller.navigate(R.id.signupFragment)
        }

        binding.loginBtn.setOnClickListener{

            checkUser()
            firebaseLogin()
        }


    }


    override fun onStarted() {

        email = binding.emailEt.text.toString().trim()
        password = binding.pswrdEt.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.emailEt.setError("Invalid Email")
        }
        else if(TextUtils.isEmpty(password)){

            binding.pswrdEt.error="Enter password"

        }
        else
        {
            firebaseLogin()
        }
    }

    override fun onSuccess() {

        fragmentManager?.popBackStack()
    }

    override fun onFailure(message: String) {


    }

    private fun checkUser() {

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){

            navcontroller.navigate(R.id.profileFragment)

        }
        else if(firebaseUser == null){
            navcontroller.navigate(R.id.signupFragment)
        }
    }

    private fun firebaseLogin() {

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {

            progressDialog.dismiss()
            val firebaseUser = firebaseAuth.currentUser
            val email =firebaseUser!!.email
            navcontroller.navigate(R.id.profileFragment)



        }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(context,"Login Failed, Sign-up",Toast.LENGTH_SHORT).show()
                navcontroller.navigate(R.id.signupFragment)
            }
    }


}