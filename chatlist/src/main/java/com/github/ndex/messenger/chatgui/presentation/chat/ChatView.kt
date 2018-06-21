package com.github.ndex.messenger.chatgui.presentation.chat

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.github.ndex.messenger.chatgui.di.AppComponentHolder
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.interfaces.ChatInfo

class ChatView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val messageList by lazy { findViewById<RecyclerView>(R.id.message_list) }
    private val sendMessageButton by lazy { findViewById<ImageButton>(R.id.send_message_button) }
    private val messageEditText by lazy { findViewById<EditText>(R.id.message_edit_text) }

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var messagesAdapter: MessageListAdapter

    init {
        inflate(context, R.layout.activity_chat, this)
        val lifecycleOwner: LifecycleOwner = context as LifecycleOwner
        val activity = context as FragmentActivity

        val appComponent = AppComponentHolder.provideAppComponent(context)
        val chatViewModelFactory = ChatViewModelFactory(appComponent)
        chatViewModel = ViewModelProviders.of(activity, chatViewModelFactory)
                .get(ChatViewModel::class.java)

        initView()

        subscribe(lifecycleOwner)
    }

    private fun initView() {
        sendMessageButton.setOnClickListener({
            chatViewModel.sendMessage(messageEditText.text.toString())
        })

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true

        messagesAdapter = MessageListAdapter(ViewHolderFactory())
        with(messageList) {
            adapter = messagesAdapter
            layoutManager = linearLayoutManager
        }

    }

    private fun subscribe(lifecycleOwner: LifecycleOwner) {
        chatViewModel.messagesList.observe(lifecycleOwner, Observer {
            if (it != null) {
                messagesAdapter.submitList(it)
                messagesAdapter.notifyDataSetChanged()
            }
        })

        chatViewModel.updateEditTextEvent.observe(lifecycleOwner, Observer {
            if (it != null) {
                messageEditText.text.clear()
                messageEditText.text.append(it)
            }
        })
    }
}
/*
private const val CHAT_ID = "CHAT_ID"
private const val CHAT_NAME = "CHAT_NAME"
fun Context.startChatActivity(chatInfo: ChatInfo) {
    val intent = Intent(this, ChatView::class.java)
            .putExtra(CHAT_ID, chatInfo.id)
            .putExtra(CHAT_NAME, chatInfo.name)
    startActivity(intent)
}
*/