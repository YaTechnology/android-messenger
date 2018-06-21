package com.github.ndex.messenger.chatgui.presentation.root

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.ndex.messenger.chatgui.di.AppComponentHolder
import com.github.ndex.messenger.chatgui.presentation.chatlist.ChatListView
import com.github.ndex.messenger.chatgui.presentation.login.LoginView

class RootMessengerScreen @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var routerViewModel: RouterViewModel
    private var currentView: View? = null

    init {
        val lifecycleOwner: LifecycleOwner = context as LifecycleOwner
        val activity = context as FragmentActivity

        val appComponent = AppComponentHolder.provideAppComponent(context.applicationContext)
        val factory = RouterViewModelFactory(appComponent)
        routerViewModel = ViewModelProviders.of(activity, factory).get(RouterViewModel::class.java)


        routerViewModel.showScreen.observe(lifecycleOwner, Observer {
            if (it != null) {
                showScreen(it)
            }
        })

        routerViewModel.onCreated()
    }

    private fun showScreen(screen: ScreenState) {
        if (currentView != null) {
            removeView(currentView)
        }

        when (screen) {
            ScreenState.LOGIN_SCREEN -> currentView = LoginView(context)
            ScreenState.CHAT_LIST_SCREEN -> currentView = ChatListView(context)
        }

        addView(currentView)
    }
}