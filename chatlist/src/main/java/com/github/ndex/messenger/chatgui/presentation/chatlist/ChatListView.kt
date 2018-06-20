package com.github.ndex.messenger.chatgui.presentation.chatlist

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.github.ndex.messenger.chatgui.di.AppComponentHolder
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatListView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var chatViewModel: ChatListViewModel
    private lateinit var chatListAdapter: ChatListAdapter
    private val chatList by lazy { findViewById<RecyclerView>(R.id.chat_list) }

    init {
        inflate(context, R.layout.activity_chat_list, this)
        val lifecycleOwner: LifecycleOwner = context as LifecycleOwner
        val activity = context as FragmentActivity

        val appComponent = AppComponentHolder.provideAppComponent(context.applicationContext)
        val chatListFactory = ChatListViewModelFactory(appComponent)
        chatViewModel = ViewModelProviders.of(activity, chatListFactory).get(ChatListViewModel::class.java)
        chatListAdapter = ChatListAdapter()

        val linearLayoutManager = LinearLayoutManager(context)

        with(chatList) {
            layoutManager = linearLayoutManager
            adapter = chatListAdapter
        }

        chatListAdapter.clickListener = object : ItemClickListener {
            override fun invoke(item: ChatInfo) {
                chatViewModel.onItemClicked(item)
            }
        }
        subscribeData(lifecycleOwner)
    }

    private fun subscribeData(lifecycleOwner: LifecycleOwner) {
        chatViewModel.chatList.observe(lifecycleOwner, Observer<List<ChatInfo>> { newValue ->
            chatListAdapter.submitList(newValue)
        })

        chatViewModel.openChatEvent.observe(lifecycleOwner, Observer<ChatInfo> { chatInfo ->
            if (chatInfo != null) {
                // TODO: place to router.
                // showChatScreen(chatInfo)
            }
        })
    }
}