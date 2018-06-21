package com.github.ndex.messenger.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import com.github.ndex.messenger.chatgui.presentation.root.RouterFabric

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chatContainer = findViewById<ConstraintLayout>(R.id.rootContainer)

        val routerFabric = RouterFabric()
        val rootView = routerFabric.createRootView(this)
        chatContainer.addView(rootView)
    }
}
