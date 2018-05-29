package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.github.ndex.messenger.demo_module.R
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.github.ndex.messenger.demo_module.App
import com.github.ndex.messenger.demo_module.presentation.chat.ChatActivity
import com.github.ndex.messenger.demo_module.presentation.chat.startChatActivity
import com.github.ndex.messenger.interfaces.ChatInfo


class ChatListActivity : AppCompatActivity() {
    private lateinit var chatViewModel: ChatListViewModel
    private lateinit var chatListAdapter: ChatListAdapter
    private val chatList by lazy { findViewById<RecyclerView>(R.id.chat_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val appComponent = (application as App).appComponent
        val chatListFactory = ChatListViewModelFactory(appComponent)
        chatViewModel = ViewModelProviders.of(this, chatListFactory).get(ChatListViewModel::class.java)

        chatList.layoutManager = LinearLayoutManager(this)
        chatListAdapter = ChatListAdapter()
        chatList.adapter = chatListAdapter
        chatListAdapter.clickListener = object : ItemClickListener {
            override fun invoke(item: ChatInfo) {
                chatViewModel.onItemClicked(item)
            }
        }
        subscribeData()
    }

    private fun subscribeData() {
        chatViewModel.getChatList().observe(this, Observer<List<ChatInfo>> { newValue ->
            chatListAdapter.submitList(newValue!!)
        })

        chatViewModel.getOpenChatEvent().observe(this, Observer<ChatInfo> { chatInfo ->
            startChatActivity(chatInfo!!)
        })
    }
}
