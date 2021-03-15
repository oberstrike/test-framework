package com.maju.rest.request.auth


interface IRequestAuth<T> {
    fun auth(): T
}


object RequestAuth {

    internal data class BearerRequestAuth(
        val bearerToken: String
    ) : IRequestAuth<String> {
        override fun auth(): String = bearerToken
    }

    internal data class BasicRequestAuth(
        val username: String,
        val password: String
    ) : IRequestAuth<Pair<String, String>> {

        override fun auth(): Pair<String, String> {
            return username to password
        }
    }


    fun basic(username: String, password: String): IRequestAuth<Pair<String, String>> {
        return BasicRequestAuth(username, password)
    }

    fun bearer(bearerToken: String): IRequestAuth<String> {
        return BearerRequestAuth(bearerToken)
    }
}
