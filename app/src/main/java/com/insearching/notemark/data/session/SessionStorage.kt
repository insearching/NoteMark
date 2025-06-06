package com.insearching.notemark.data.session

interface SessionStorage {
    fun get(): TokenPair?
    fun update(newTokens: TokenPair)
}