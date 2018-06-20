package com.github.ndex.messenger.chatgui.presentation.chatlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatListViewHolder(itemView: View, listener: ViewHolderClickListener) : RecyclerView.ViewHolder(itemView) {
    private val chatName: TextView = itemView.findViewById(R.id.chat_name)

    fun bindView(chatInfo: ChatInfo) {
        chatName.text = chatInfo.name
    }

    init {
        itemView.setOnClickListener({
            listener.onViewHolderClicked(adapterPosition)
        })
    }
}

fun createChatListViewHolder(parent: ViewGroup, clickListener: ViewHolderClickListener) =
        ChatListViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.chat_item_layout, parent, false),
                clickListener)