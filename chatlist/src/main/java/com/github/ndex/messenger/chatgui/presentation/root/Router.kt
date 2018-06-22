package com.github.ndex.messenger.chatgui.presentation.root

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.github.ndex.messenger.chatgui.di.AppComponentHolder
import com.github.ndex.messenger.chatgui.presentation.chat.ChatView
import com.github.ndex.messenger.chatgui.presentation.chatlist.ChatListView
import com.github.ndex.messenger.chatgui.presentation.login.LoginView

class Router(private val context: Context, private val rootView: FrameLayout) {
    private lateinit var routerViewModel: RootViewModel
    private var currentView: View? = null

    init {
        val lifecycleOwner: LifecycleOwner = context as LifecycleOwner
        val activity = context as FragmentActivity

        val appComponent = AppComponentHolder.provideAppComponent(context.applicationContext)
        val factory = RouterViewModelFactory(appComponent)
        routerViewModel = ViewModelProviders.of(activity, factory).get(RootViewModel::class.java)


        routerViewModel.showScreen.observe(lifecycleOwner, Observer {
            if (it != null) {
                showScreen(it)
            }
        })

        routerViewModel.onCreated()
    }

    private fun showScreen(screen: ScreenState) {
        if (currentView != null) {
            rootView.removeView(currentView)
        }

        currentView = when (screen) {
            ScreenState.LOGIN_SCREEN -> LoginView(context)
            ScreenState.CHAT_LIST_SCREEN -> ChatListView(context)
            else -> ChatView(context)
        }
        currentView!!.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        rootView.addView(currentView)
    }

    fun onBackPressed(): Boolean = routerViewModel.onBackPressed()
}