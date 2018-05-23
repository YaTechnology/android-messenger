package com.github.ndex.messenger.amqpmesenger.models


import com.github.ndex.messenger.interfaces.ChatInfo
import java.util.*

/**
 * Created by grishberg on 21.05.18.
 */
class ChatListCommand(val cmd: Int,
                      val userId: String = "",
                      val chatList: List<ChatInfo> = ArrayList()) {
    companion object {
        val CREATE_CHAT = 1
        val INVITE_TO_CHAT = 2
        val ASSIGN_TO_CHAT = 3
        val GET_CHAT_LIST = 4
    }
}