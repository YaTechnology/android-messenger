package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatListAdapter : RecyclerView.Adapter<ChatListViewHolder>() {
    var chatList: List<ChatInfo> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
            println("set data: field $")
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder.createInstance(parent)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bindView(chatList[position])
    }
}