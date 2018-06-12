package com.github.ndex.messenger.demo_module.di

import com.github.ndex.messenger.demo_module.presentation.chat.ChatViewModelFactory
import com.github.ndex.messenger.demo_module.presentation.chatlist.ChatListViewModelFactory
import com.github.ndex.messenger.demo_module.presentation.login.LoginViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(factory: ChatListViewModelFactory)
    fun inject(factory: ChatViewModelFactory)
    fun inject(factory: LoginViewModelFactory)
}