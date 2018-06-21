package com.github.ndex.messenger.chatgui.di

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.github.ndex.messenger.chatgui.presentation.chat.ChatViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.chatlist.ChatListViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.login.LoginViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.root.RouterViewModelFactory

/**
 * Provides AppComponent.
 */
class AppComponentHolder {
    companion object {
        private var sAppComponent: AppComponent? = null
        @VisibleForTesting
        internal fun setAppComponent(appComponentForTesting: AppComponent) {
            sAppComponent = appComponentForTesting
        }

        internal fun provideAppComponent(appContext: Context): AppComponent {
            if (sAppComponent == null) {
                sAppComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(appContext))
                        .build()
            }
            return sAppComponent!!
        }
    }
}

private class AppComponentStub : AppComponent {
    override fun inject(factory: ChatListViewModelFactory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject(factory: ChatViewModelFactory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject(factory: LoginViewModelFactory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject(factory: RouterViewModelFactory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}