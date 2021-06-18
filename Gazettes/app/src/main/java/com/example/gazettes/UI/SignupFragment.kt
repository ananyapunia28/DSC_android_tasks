package com.example.gazettes.UI

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gazettes.R
import com.example.gazettes.data.User
import com.example.gazettes.databinding.FragmentSignupBinding
import com.example.gazettes.viewmodels.SignViewModel
import com.example.gazettes.viewmodels.SignupListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern


class SignupFragment : Fragment(R.layout.fragment_signup), SignupListener {
    lateinit var binding: FragmentSignupBinding
    lateinit var navController: NavController
    lateinit var progressDialog: ProgressDialog
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var email: String
    lateinit var pass: String
    lateinit var cpass: String
    lateinit var age: String
    lateinit var phone: String
    lateinit var address: String
    lateinit var bio: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val signupviewModel = ViewModelProviders.of(this).get(SignViewModel::class.java)
        binding.signupdetails = signupviewModel
        signupviewModel.signupListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

//        binding.SignupBtn.setOnClickListener {
//
//            on_Started()
//        firebaseAuth = FirebaseAuth.getInstance()
//    }
    }
    private fun firebaseSignup() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            progressDialog.dismiss()

            val user = User(email,pass,cpass,age,phone,address,bio)

//            var user = Profile(bio, age, phone, address, bio, email)
            FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user).addOnCompleteListener {
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_signupFragment_to_loginFragment2)

                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed! Try again", Toast.LENGTH_SHORT).show()

                }

        }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(context, "Signup failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }
    }


    override fun on_Started() {

        email = binding.emailet.text.toString().trim()
        pass = binding.pswrdet.text.toString().trim()
        cpass = binding.cpswrdet.text.toString().trim()
        age = binding.ageet.text.toString().trim()
        phone = binding.phnoet.text.toString().trim()
        address = binding.addresset.text.toString().trim()
        bio = binding.bioEt.text.toString().trim()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(context, "INVALID EMAIL", Toast.LENGTH_SHORT).show()

        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show()
        } else if (!validatepass(pass)) {
            Toast.makeText(
                context,
                "Password must contain one uppercase, one lowercase, a digit and it's length must be in between 8-12",
                Toast.LENGTH_SHORT
            ).show()
            binding.pswrdet.error = "Password must contain one uppercase, one lowercase, a digit and it's length must be in between 8-12"
        } else if (cpass != pass) {
            Toast.makeText(context, "Passwords mismatched", Toast.LENGTH_SHORT).show()
            binding.cpswrdet.error = "Passwords don't match"

        } else if (!isValidPhoneNumber(phone) ||phone.length>10 || phone.length<10) {
            Toast.makeText(context, "Enter Valid Phone no.", Toast.LENGTH_SHORT).show()
            binding.phnoet.error = "Enter 10 digit phone no."
        } else if (age.length > 2) {
            Toast.makeText(context, "Enter Valid Age", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "Enter Phone no.", Toast.LENGTH_SHORT).show()
        } else {
            firebaseSignup()
            
        }

    }

    fun validatepass(pass: String): Boolean {
        var regexes = "^(?=.*[0-9])(?=.*[A-Z]).{8,12}$"
        var p: Pattern = Pattern.compile(regexes)
        if (pass.isEmpty()) return false
        var m = p.matcher(pass)
        return m.matches()
    }

    private fun isValidPhoneNumber(phoneNumber: CharSequence): Boolean {
        return if (!TextUtils.isEmpty(phoneNumber)) {
            Patterns.PHONE.matcher(phoneNumber).matches()
        } else false
    }


    override fun onSuccess() {
        Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show()

    }

    override fun onFailure(message: String) {


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}
