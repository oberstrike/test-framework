package com.maju.rest

import com.maju.rest.request.GetRequest
import com.maju.rest.request.IGetRequest
import com.maju.rest.request.IPostRequest
import com.maju.rest.request.PostRequest
import io.restassured.http.ContentType

interface IRequestBuilder {
    fun build(): IGetRequest
}

interface IGetRequestBuilder : IRequestBuilder {
    fun path(path: String): IRequestBuilder

    fun auth(restRequestAuth: RestRequestAuth<*>): IRequestBuilder

    fun params(params: Map<String, *>): IRequestBuilder

    fun param(params: Pair<String, *>): IRequestBuilder
}

interface IPostRequestBuilder : IRequestBuilder, IGetRequestBuilder {

    fun contentType(contentType: ContentType): IRequestBuilder

    fun body(body: String): IRequestBuilder

}

class RestRequestBuilder private constructor() {

    class GetRequestBuilder private constructor() : IGetRequestBuilder {
        companion object {
            fun create(path: String): IGetRequestBuilder = GetRequestBuilder().apply { path(path) }
        }

        private val request = GetRequest()

        override fun path(path: String): IGetRequestBuilder = apply {
            request.path = path
        }

        override fun auth(restRequestAuth: RestRequestAuth<*>): IGetRequestBuilder = apply {
            request.restRequestAuth = restRequestAuth
        }

        override fun params(params: Map<String, *>): IGetRequestBuilder = apply {
            request.params = params
        }

        override fun param(params: Pair<String, *>): IRequestBuilder = apply {
            request.params = mapOf(params)

        }

        override fun build(): IGetRequest {
            return request
        }
    }

    class PostRequestBuilder private constructor() : IPostRequestBuilder {
        private val request = PostRequest()

        companion object {
            fun create(path: String): IPostRequestBuilder = PostRequestBuilder().apply { path(path) }
        }

        override fun contentType(contentType: ContentType): IPostRequestBuilder = apply {
            request.contentType = contentType
        }

        override fun body(body: String): IPostRequestBuilder = apply {
            request.body = body
        }

        override fun path(path: String): IPostRequestBuilder = apply {
            request.path = path
        }

        override fun param(params: Pair<String, *>): IPostRequestBuilder = apply {
            request.params = mapOf(params)

        }

        override fun auth(restRequestAuth: RestRequestAuth<*>): IPostRequestBuilder = apply {
            request.restRequestAuth = restRequestAuth
        }

        override fun params(params: Map<String, *>): IPostRequestBuilder = apply {
            request.params = params
        }

        override fun build(): IGetRequest {
            return request
        }
    }

    companion object {
        fun get(path: String = "", block: IGetRequestBuilder.() -> IRequestBuilder): IGetRequest {
            val getRequestBuilder = GetRequestBuilder.create(path)
            block.invoke(getRequestBuilder)
            return getRequestBuilder.build()
        }

        fun post(path: String = "", block: IPostRequestBuilder.() -> IRequestBuilder): IPostRequest {
            val postRequestBuilder = PostRequestBuilder.create(path)
            block.invoke(postRequestBuilder)
            return postRequestBuilder.build() as IPostRequest
        }

    }

}