package com.example.instagramc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramc.databinding.FragmentPostBinding
import com.example.instagramc.Posts



class PostFragment: Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var recyclerview1: RecyclerView
    private lateinit var binding: FragmentPostBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)
        binding.chatButton.setOnClickListener {
            navController.navigate(R.id.action_postFragment_to_chatFragment)
        }
        val postlist = arrayListOf(
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.walk),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.walk),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.alan),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.iconalan),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.walk),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.iconalan),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.iconalan),
            Posts("alanwalkermusic", R.drawable.alan, R.drawable.alan)
        )
        val postAdapter = PostAdapter(postlist)
        recyclerview1 = binding.posts
        recyclerview1.setHasFixedSize(true)
        recyclerview1.layoutManager = LinearLayoutManager(activity)
        recyclerview1.adapter = postAdapter
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPostBinding.inflate(layoutInflater)
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<ImageButton>(R.id.chatButton).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        binding = FragmentPostBinding.inflate(layoutInflater)
        binding.chatButton.setOnClickListener {
            navController.navigate(R.id.action_postFragment_to_chatFragment)
        }


    }


}




