package com.github.ndex.messenger.interfaces

interface Message {
    fun getBody(): ByteArray
    fun getText(): String
}