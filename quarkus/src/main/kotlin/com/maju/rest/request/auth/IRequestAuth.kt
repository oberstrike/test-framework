package com.maju.rest.request.auth


interface IRequestAuth<T> {
    fun auth(): T
}


data class BasicRequestAuth(
    val username: String,
    val password: String
) : IRequestAuth<Pair<String, String>> {

    override fun auth(): Pair<String, String> {
        return username to password
    }
}

data class BearerRequestAuth(
    val bearerToken: String
) : IRequestAuth<String> {
    override fun auth(): String = bearerToken
}

object RequestAuth {

    fun basic(username: String, password: String): IRequestAuth<Pair<String, String>> {
        return BasicRequestAuth(username, password)
    }

    fun bearer(bearerToken: String): IRequestAuth<String> {
        return BearerRequestAuth(bearerToken)

    }
}