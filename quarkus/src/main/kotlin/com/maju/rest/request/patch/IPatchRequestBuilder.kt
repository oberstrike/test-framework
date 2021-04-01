package com.maju.rest.request.patch

import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.post.IPostRequestBuilder
import com.maju.rest.request.post.PostRequestBuilder

interface IPatchRequestBuilder : IPostRequestBuilder {
    override fun build(): IPatchRequest

}

class PatchRequestBuilder() : IPatchRequestBuilder,
    PostRequestBuilder() {
    companion object {
        fun create(): IPatchRequestBuilder = PatchRequestBuilder()
    }

    override fun build(): IPatchRequest {
        return PatchRequest(super.build(), path)
    }
}

fun patch(block: IPatchRequestBuilder.() -> IRequestBuilder): IPatchRequest {
    val patchRequestBuilder = PatchRequestBuilder.create()
    block.invoke(patchRequestBuilder)
    return patchRequestBuilder.build()
}
