package com.example.instagramc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramc.databinding.ChatsampleBinding
import com.example.instagramc.Chats

data class ChatAdapter(val userlist: ArrayList<Chats>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding: ChatsampleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatAdapter.ChatViewHolder {
        return ChatViewHolder(
            ChatsampleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatAdapter.ChatViewHolder, position: Int) {
        holder.binding.img1.setImageResource(userlist[position].userimg)
        holder.binding.t1.text = userlist[position].username
        holder.binding.msg.text = userlist[position].message
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}