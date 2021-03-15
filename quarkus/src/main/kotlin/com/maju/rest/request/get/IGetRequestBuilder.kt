package com.maju.rest.request.get

import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.RestRequestFactory

interface IGetRequestBuilder : IBaseRequestBuilder {
    override fun build(): IGetRequest
}

open class GetRequestBuilder(protected open val request: IGetRequest) : IGetRequestBuilder {

    companion object {
        fun create(path: String): IGetRequestBuilder = GetRequestBuilder(GetRequest()).apply { path(path) }
    }


    override fun path(path: String): IBaseRequestBuilder = apply {
        request.path = path
    }

    override fun auth(requestAuth: IRequestAuth<*>): IBaseRequestBuilder = apply {
        request.requestAuth = requestAuth
    }

    override fun params(params: Map<String, *>): IBaseRequestBuilder = apply {
        request.params = params
    }

    override fun headers(headers: Map<String, String>): IBaseRequestBuilder = apply {
        request.headers = headers
    }

    override fun param(params: Pair<String, *>): IBaseRequestBuilder = apply {
        request.params = mapOf(params)

    }

    override fun build(): IGetRequest {
        return request
    }
}

fun RestRequestFactory.get(path: String = "",
                           block: IGetRequestBuilder.() -> IRequestBuilder): IGetRequest {
    val getRequestBuilder = GetRequestBuilder.create(path)
    block.invoke(getRequestBuilder)
    return getRequestBuilder.build()
}
