package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.github.ndex.messenger.interfaces.ChatInfo

typealias ItemClickListener = (ChatInfo) -> Unit

class ChatListAdapter : ListAdapter<ChatInfo, ChatListViewHolder>(DiffCallbackImpl()) {

    var clickListener: ItemClickListener = ClickListenerStub()
    private val viewClickListener: ViewHolderClickListener = ViewClickListener()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return createChatListViewHolder(parent, viewClickListener)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    private inner class ViewClickListener : ViewHolderClickListener {
        override fun onViewHolderClicked(itemPosition: Int) {
            clickListener.invoke(getItem(itemPosition))
        }
    }

    private class DiffCallbackImpl : DiffUtil.ItemCallback<ChatInfo>() {

        override fun areItemsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean = oldItem == newItem
    }

    private class ClickListenerStub : ItemClickListener {
        override fun invoke(p1: ChatInfo) {
            /* stub */
        }
    }
}