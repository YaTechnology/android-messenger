package com.github.ndex.messenger.interfaces

/**
 * Represent chat info.
 */
data class ChatInfo(val id: String, val name: String, val users: List<User>)