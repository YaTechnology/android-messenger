package com.github.ndex.messenger.amqpmesenger.common

interface Serializer {
    fun serialize(src: Any): ByteArray

    fun <T> deserialize(bytes: ByteArray, type: Class<T>): T
}
