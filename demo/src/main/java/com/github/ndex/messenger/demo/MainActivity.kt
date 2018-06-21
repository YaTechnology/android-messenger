package com.github.ndex.messenger.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.github.ndex.messenger.chatgui.presentation.root.RootMessengerScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chatContainer = findViewById<FrameLayout>(R.id.chatContainer)

        val rootMessengerView = RootMessengerScreen(this)
        chatContainer.addView(rootMessengerView)
    }
}
