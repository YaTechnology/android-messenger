package com.github.ndex.messenger.demo_module.di

import android.content.Context
import com.github.ndex.messenger.demo_module.data.HistoryRepository
import com.github.ndex.messenger.demo_module.data.HistoryRepositoryImpl
import com.github.ndex.messenger.demo_module.domain.ChatService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("unused")
class AppModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context = appContext

    @Singleton
    @Provides
    fun provideChatService(historyRepository: HistoryRepository): ChatService =
            ChatService(historyRepository)

    @Singleton
    @Provides
    fun provideHistoryRepository(appContext: Context): HistoryRepository =
            HistoryRepositoryImpl(appContext)
}