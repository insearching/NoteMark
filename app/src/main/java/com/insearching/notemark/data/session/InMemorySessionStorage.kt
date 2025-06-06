package com.insearching.notemark.data.session

class InMemorySessionStorage : SessionStorage {
    private var tokenPair: TokenPair? = null

    override fun get(): TokenPair? = tokenPair

    override fun update(newTokens: TokenPair) {
        tokenPair = newTokens
    }
}