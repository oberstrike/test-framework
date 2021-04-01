package com.maju.rest.request.get

import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder

interface IGetRequestBuilder : IBaseRequestBuilder {
    override fun build(): IGetRequest
}

open class GetRequestBuilder : IGetRequestBuilder {

    var requestAuth: IRequestAuth<*>? = null

    var params: Map<String, *>? = null

    var headers: Map<String, String>? = null

    var cookies: Map<String, *>? = null

    var path: String = ""

    companion object {
        fun create(): IGetRequestBuilder = GetRequestBuilder()
    }


    override fun path(path: String): IBaseRequestBuilder = apply {
        this.path = path
    }

    override fun auth(requestAuth: IRequestAuth<*>): IBaseRequestBuilder = apply {
        this.requestAuth = requestAuth
    }

    override fun params(params: Map<String, *>): IBaseRequestBuilder = apply {
        this.params = params
    }

    override fun headers(headers: Map<String, String>): IBaseRequestBuilder = apply {
        this.headers = headers
    }


    override fun param(params: Pair<String, *>): IBaseRequestBuilder = apply {
        this.params = mapOf(params)

    }

    override fun build(): IGetRequest {
        return GetRequest(path).let {
            it.params = params
            it.cookies = cookies
            it.headers = headers
            it.requestAuth = requestAuth
            it
        }
    }
}

fun get(block: IGetRequestBuilder.() -> IRequestBuilder): IGetRequest {
    val getRequestBuilder = GetRequestBuilder.create()
    block.invoke(getRequestBuilder)
    return getRequestBuilder.build()
}
