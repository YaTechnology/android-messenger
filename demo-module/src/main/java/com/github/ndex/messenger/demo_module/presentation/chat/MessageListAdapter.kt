package com.github.ndex.messenger.demo_module.presentation.chat

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.github.ndex.messenger.interfaces.Message

class MessageListAdapter(private val factory: ViewHolderFactory) : ListAdapter<Message, MessageViewHolder>(DiffCallbackImpl()) {

    override fun getItemViewType(position: Int): Int {
        return factory.getItemType(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
            factory.createViewHolder(parent, viewType)


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    private class DiffCallbackImpl : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.time == newItem.time && oldItem.fromChatId == newItem.fromChatId


        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.time == newItem.time && oldItem.fromChatId == newItem.fromChatId &&
                        oldItem.text == newItem.text
    }
}