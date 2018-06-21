package com.github.ndex.messenger.chatgui.presentation.root

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout

class RouterFabric {
    private var router: Router? = null

    /**
     * Provides root view.
     */
    fun createRootView(context: Context): View {
        val rootView = FrameLayout(context)
        rootView.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        router = Router(context, rootView)
        return rootView
    }
}