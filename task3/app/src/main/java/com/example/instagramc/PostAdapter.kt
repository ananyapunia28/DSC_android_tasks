package com.example.instagramc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.instagramc.databinding.PostviewBinding

data class PostAdapter(val postlist: ArrayList<Posts>) :
    RecyclerView.Adapter<PostAdapter.PostviewHolder>() {

    class PostviewHolder(val binding: PostviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostviewHolder {

        return PostviewHolder(
            PostviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostviewHolder, position: Int) {
        holder.binding.alnwlkr.text = postlist[position].username
        holder.binding.postimg.setImageResource(postlist[position].userimg)
        holder.binding.icn1.setImageResource(postlist[position].userpost)
    }

    override fun getItemCount(): Int {
        return postlist.size
    }
}



