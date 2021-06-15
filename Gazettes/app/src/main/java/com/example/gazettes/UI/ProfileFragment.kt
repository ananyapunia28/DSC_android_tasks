package com.example.gazettes.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gazettes.R
import com.example.gazettes.databinding.FragmentLoginBinding
import com.example.gazettes.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var user: FirebaseUser
    lateinit var database: DatabaseReference
    lateinit var userID:String
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: FragmentProfileBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()



    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            userID = firebaseUser!!.uid
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(userID).get().addOnSuccessListener {
                if(it != null){
                    binding.prof1.text = it.child("email").value.toString()
                    binding.prof2.text = it.child("age").value.toString()
                    binding.prof3.text = it.child("address").value.toString()
                    binding.prof4.text = it.child("phone").value.toString()
                    binding.prof5.text = it.child("bio").value.toString()


                }
                else {
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed ",Toast.LENGTH_SHORT).show()

                }
        }
        else {
            navController.navigate(R.id.mainFragment)

        }

    }
}
