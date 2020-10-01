package com.maju.rest.request.pach

import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.post.IPostRequest
import com.maju.rest.request.post.IPostRequestBuilder
import com.maju.rest.request.post.PostRequestBuilder

interface IPatchRequestBuilder : IPostRequestBuilder {
    override fun build(): IPatchRequest

}

class PatchRequestBuilder(override val request: IPatchRequest = PatchRequest()) : IPatchRequestBuilder,
    PostRequestBuilder(request) {
    companion object {
        fun create(path: String): IPatchRequestBuilder = PatchRequestBuilder()
    }

    override fun build(): IPatchRequest {
        return request
    }
}

fun RestRequestFactory.patch(path: String = "", block: IPatchRequestBuilder.() -> IRequestBuilder): IPatchRequest {
    val patchRequestBuilder = PatchRequestBuilder.create(path)
    block.invoke(patchRequestBuilder)
    return patchRequestBuilder.build()
}