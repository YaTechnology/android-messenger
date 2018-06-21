package com.github.ndex.messenger.chatgui.di

import com.github.ndex.messenger.chatgui.presentation.chat.ChatViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.chatlist.ChatListViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.login.LoginViewModelFactory
import com.github.ndex.messenger.chatgui.presentation.root.RouterViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(factory: ChatListViewModelFactory)
    fun inject(factory: ChatViewModelFactory)
    fun inject(factory: LoginViewModelFactory)
    fun inject(factory: RouterViewModelFactory)
}