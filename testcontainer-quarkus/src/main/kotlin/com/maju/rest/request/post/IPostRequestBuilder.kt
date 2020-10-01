package com.maju.rest.request.post

import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.get.GetRequestBuilder
import com.maju.rest.request.get.IGetRequestBuilder
import io.restassured.http.ContentType

interface IPostRequestBuilder : IGetRequestBuilder {
    fun contentType(contentType: String): IPostRequestBuilder

    fun body(body: String): IPostRequestBuilder

    override fun build(): IPostRequest

}


open class PostRequestBuilder(override val request: IPostRequest = PostRequest()) : IPostRequestBuilder, GetRequestBuilder(request) {


    companion object {
        fun create(path: String): IPostRequestBuilder = PostRequestBuilder().apply { path(path) }
    }

    override fun headers(headers: Map<String, String>): IBaseRequestBuilder = apply {
        request.headers = headers
    }

    override fun contentType(contentType: String): IPostRequestBuilder = apply {
        request.contentType = contentType
    }

    override fun body(body: String): IPostRequestBuilder = apply {
        request.body = body
    }

    override fun build(): IPostRequest {
        return request
    }

}

fun RestRequestFactory.post(path: String = "", block: IPostRequestBuilder.() -> IRequestBuilder): IPostRequest {
    val postRequestBuilder = PostRequestBuilder.create(path)
    block.invoke(postRequestBuilder)
    return postRequestBuilder.build()
}