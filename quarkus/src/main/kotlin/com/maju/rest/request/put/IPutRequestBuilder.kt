package com.maju.rest.request.put

import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.pach.IPatchRequest
import com.maju.rest.request.pach.PatchRequest
import com.maju.rest.request.post.IPostRequestBuilder
import com.maju.rest.request.post.PostRequestBuilder

interface IPutRequestBuilder : IPostRequestBuilder {
    override fun build(): IPutRequest

}

class PutRequestBuilder(override val request: IPutRequest = PutRequest()) : IPutRequestBuilder,
    PostRequestBuilder(request) {
    companion object {
        fun create(path: String): IPutRequestBuilder = PutRequestBuilder().apply { path(path) }
    }

    override fun build(): IPutRequest {
        return request
    }
}

fun RestRequestFactory.put(path: String = "", block: IPutRequestBuilder.() -> IRequestBuilder)
        : IPutRequest {
    val putRequestBuilder = PutRequestBuilder.create(path)
    block.invoke(putRequestBuilder)
    return putRequestBuilder.build()
}
