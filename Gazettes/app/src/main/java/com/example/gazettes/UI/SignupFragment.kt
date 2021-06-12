package com.example.gazettes.UI

import android.app.ProgressDialog
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gazettes.R
import com.example.gazettes.data.Profile
import com.example.gazettes.databinding.FragmentLoginBinding
import com.example.gazettes.databinding.FragmentSignupBinding
import com.example.gazettes.viewmodels.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern


class SignupFragment : Fragment() {

    lateinit var binding:FragmentSignupBinding
    lateinit var navController: NavController
    lateinit var progressDialog:ProgressDialog
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var email:String
    lateinit var pass:String
    lateinit var cpass:String
    lateinit var age:String
    lateinit var phone:String
    lateinit var address:String
    lateinit var bio:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val signupviewModel= ViewModelProviders.of(this).get(SignupViewModel::class.java)
        signupviewModel.signupListener= this
        binding.signupdetails = SignupViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.SignupBtn.setOnClickListener {

            on_Started()
        }
    }

    private fun firebaseSignup() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            progressDialog.dismiss()

            val firebaseAuth = firebaseAuth.currentUser
            var user = Profile( bio, age, phone,address,bio,email)
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user).addOnCompleteListener{

                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed! Try again",Toast.LENGTH_SHORT).show()

                }
            navController.navigate(R.id.mainFragment)

        }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(context,"Signup failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }


    fun on_Started() {

        email = binding.emailet.text.toString().trim()
        pass = binding.pswrdet.text.toString().trim()
        cpass = binding.cpswrdet.text.toString().trim()
        age = binding.ageet.text.toString().trim()
        phone = binding.phnoet.text.toString().trim()
        address = binding.addresset.text.toString().trim()
        bio = binding.bioEt.text.toString().trim()


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.emailet.error = "Invalid Email"

        }
        else if(TextUtils.isEmpty(pass)){
            binding.pswrdet.error ="Please enter password"
        }

        else if(!validatepass(pass)){
            binding.pswrdet.error = "Password must contain one uppercase, one lowercase, a digit and it's length must be in between 8-12"
        }

        else if(cpass != pass){
            binding.cpswrdet.error = "Password and confirm password should be same."

        }
        else if( age.length >2 ){
            binding.age.error = "Enter a Valid Age"
        }
        else if(TextUtils.isEmpty(phone) ){
            binding.phnoet.error = "Please Enter your Phone number"
        }

        else{
            firebaseSignup()
        }

    }

    fun validatepass(pass: String) : Boolean{
        var regexes = "^(?=.*[0-9])(?=.*[A-Z]).{8,12}$"
        var p : Pattern = Pattern.compile(regexes)
        if(pass.isEmpty()) return false
        var m = p.matcher(pass)
        return m.matches()
    }


    fun on_Success() {
        Toast.makeText(context,"User Registered",Toast.LENGTH_SHORT).show()

    }

    fun on_Failure(message: String) {


    }

}