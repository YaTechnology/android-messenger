package com.github.ndex.messenger.demo_module.presentation.chat

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.ndex.messenger.demo_module.App
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatActivity : AppCompatActivity() {
    private val messageList by lazy { findViewById<RecyclerView>(R.id.message_list) }

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatId: String = intent.getStringExtra(CHAT_ID)
        val appComponent = (application as App).appComponent
        val chatViewModelFactory = ChatViewModelFactory(appComponent, chatId)
        chatViewModel = ViewModelProviders.of(this, chatViewModelFactory)
                .get(ChatViewModel::class.java)

    }
}

private const val CHAT_ID = "CHAT_ID"
fun Context.startChatActivity(chatInfo: ChatInfo) =
        startActivity(Intent(this, ChatActivity::class.java).putExtra(CHAT_ID, chatInfo.id))

