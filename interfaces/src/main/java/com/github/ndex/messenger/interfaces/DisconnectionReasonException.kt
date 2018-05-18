package com.github.ndex.messenger.interfaces

class DisconnectionReasonException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
}