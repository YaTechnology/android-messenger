package com.github.ndex.messenger.amqpmesenger.common

import com.google.gson.Gson

class GsonSerializer : Serializer {
    private val gson = Gson()

    override fun serialize(src: Any): ByteArray = gson.toJson(src).toByteArray()

    override fun <T> deserialize(bytes: ByteArray, type: Class<T>): T {
        val json = String(bytes)
        return gson.fromJson(json, type)
    }
}