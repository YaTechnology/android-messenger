package com.github.ndex.messenger.chatgui.di

import android.content.Context
import com.github.ndex.messenger.chatgui.data.HistoryRepository
import com.github.ndex.messenger.chatgui.data.HistoryRepositoryImpl
import com.github.ndex.messenger.chatgui.data.SettingsRepository
import com.github.ndex.messenger.chatgui.domain.ChatService
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
    fun provideChatService(historyRepository: HistoryRepository,
                           settingsRepository: SettingsRepository): ChatService =
            ChatService(historyRepository, settingsRepository)

    @Singleton
    @Provides
    fun provideHistoryRepository(appContext: Context): HistoryRepository =
            HistoryRepositoryImpl(appContext)

    @Singleton
    @Provides
    fun provideSettingsRepository(appContext: Context) = SettingsRepository(appContext)
}