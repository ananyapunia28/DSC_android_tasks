package com.example.instagramc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramc.databinding.FragmentChatBinding
import com.example.instagramc.Chats
import com.example.instagramc.ChatAdapter


class ChatFragment : Fragment() {
    private lateinit var chatrecyclerview: RecyclerView
    private lateinit var binding: FragmentChatBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.addBtnbck.setOnClickListener {
            navController.navigate(R.id.action_chatFragment_to_postFragment)
        }
        val userlist = arrayListOf(
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh"),
            Chats(R.drawable.alan, "Alan Walker", "Hey Marsh")
        )
        val chatadapter = ChatAdapter(userlist)
        chatrecyclerview = binding.chats
        chatrecyclerview.setHasFixedSize(true)
        chatrecyclerview.layoutManager = LinearLayoutManager(activity)
        chatrecyclerview.adapter = chatadapter
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.inflate(layoutInflater)
    }
}

