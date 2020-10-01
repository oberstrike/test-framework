package com.maju.rest.request.auth


interface RestRequestAuth<T> {
    fun auth(): T
}


data class BasicRestRequestAuth(
    val username: String,
    val password: String
) : RestRequestAuth<Pair<String, String>> {

    override fun auth(): Pair<String, String> {
        return username to password
    }
}

data class BearerRestRequestAuth(
    val bearerToken: String
) : RestRequestAuth<String> {
    override fun auth(): String = bearerToken
}
