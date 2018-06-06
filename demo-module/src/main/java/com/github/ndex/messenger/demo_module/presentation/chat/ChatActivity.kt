package com.github.ndex.messenger.demo_module.presentation.chat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.github.ndex.messenger.demo_module.App
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatActivity : AppCompatActivity() {
    private val messageList by lazy { findViewById<RecyclerView>(R.id.message_list) }
    private val sendMessageButton by lazy { findViewById<ImageButton>(R.id.send_message_button) }
    private val messageEditText by lazy { findViewById<EditText>(R.id.message_edit_text) }

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var messagesAdapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val appComponent = (application as App).appComponent
        val chatViewModelFactory = ChatViewModelFactory(appComponent)
        chatViewModel = ViewModelProviders.of(this, chatViewModelFactory)
                .get(ChatViewModel::class.java)

        initView()

        subscribe()

    }

    private fun initView() {
        sendMessageButton.setOnClickListener({
            chatViewModel.sendMessage(messageEditText.text.toString())
        })

        messagesAdapter = MessageListAdapter(ViewHolderFactory())
        messageList.adapter = messagesAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        messageList.layoutManager = layoutManager
    }

    private fun subscribe() {
        chatViewModel.messagesList.observe(this, Observer {
            if (it != null) {
                messagesAdapter.submitList(it)
                messagesAdapter.notifyDataSetChanged()
            }
        })

        chatViewModel.updateEditTextEvent.observe(this, Observer {
            if (it != null) {
                messageEditText.text.clear()
                messageEditText.text.append(it)
            }
        })
    }
}

private const val CHAT_ID = "CHAT_ID"
private const val CHAT_NAME = "CHAT_NAME"
fun Context.startChatActivity(chatInfo: ChatInfo) {
    val intent = Intent(this, ChatActivity::class.java)
            .putExtra(CHAT_ID, chatInfo.id)
            .putExtra(CHAT_NAME, chatInfo.name)
    startActivity(intent)
}

