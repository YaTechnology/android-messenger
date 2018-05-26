package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatListViewHolder : RecyclerView.ViewHolder {
    companion object {
        fun createInstance(parent: ViewGroup?):
                ChatListViewHolder = ChatListViewHolder(
                LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.chat_item_layout, parent, false))
    }

    private val chatName: TextView

    constructor(itemView: View?) : super(itemView) {
        chatName = itemView!!.findViewById(R.id.chat_name)
    }

    fun bindView(chatInfo: ChatInfo) {
        chatName.text = chatInfo.name
    }
}