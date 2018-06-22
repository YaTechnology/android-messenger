package com.github.ndex.messenger.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import com.github.ndex.messenger.chatgui.presentation.root.MessengerInteraction

class MainActivity : AppCompatActivity() {
    private lateinit var messengerInteraction: MessengerInteraction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chatContainer = findViewById<ConstraintLayout>(R.id.rootContainer)

        messengerInteraction = MessengerInteraction()
        val rootView = messengerInteraction.provideRootView(this)
        chatContainer.addView(rootView)
    }

    override fun onBackPressed() {
        if (messengerInteraction.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }
}
