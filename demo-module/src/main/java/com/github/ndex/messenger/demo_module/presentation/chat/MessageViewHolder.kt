package com.github.ndex.messenger.demo_module.presentation.chat

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.Message

const val INPUT_MESSAGE_TYPE = 1
const val OUTPUT_MESSAGE_TYPE = 2

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val messageText = itemView.findViewById<TextView>(R.id.message_text)
    fun bindView(item: Message) {
        messageText.text = item.text
    }

}