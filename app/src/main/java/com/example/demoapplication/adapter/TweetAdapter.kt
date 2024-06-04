package com.example.demoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapplication.R
import com.example.demoapplication.model.TweetResponse
import com.google.android.material.textview.MaterialTextView

class TweetAdapter():RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    private val list:ArrayList<TweetResponse> = ArrayList()

    fun setList(list:List<TweetResponse>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class TweetViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tweet:MaterialTextView = itemView.findViewById(R.id.tweet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tweet,parent,false)
        return TweetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val current = list[position]
        holder.tweet.text = current.tweet
    }
}