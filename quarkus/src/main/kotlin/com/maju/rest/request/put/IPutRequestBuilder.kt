package com.maju.rest.request.put

import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.post.IPostRequestBuilder
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestBuilder

interface IPutRequestBuilder : IPostRequestBuilder {
    override fun build(): IPutRequest

}

class PutRequestBuilder : IPutRequestBuilder,
    PostRequestBuilder() {
    companion object {
        fun create(): IPutRequestBuilder = PutRequestBuilder()
    }

    override fun build(): IPutRequest {
        return PutRequest(path).let {
            it.body = body
            it.contentType = contentType
            it.params = params
            it.cookies = cookies
            it.headers = headers
            it.requestAuth = requestAuth
            it.multipartFile = multipartFile
            it
        }
    }
}

fun put(block: IPutRequestBuilder.() -> IRequestBuilder)
        : IPutRequest {
    val putRequestBuilder = PutRequestBuilder.create()
    block.invoke(putRequestBuilder)
    return putRequestBuilder.build()
}
