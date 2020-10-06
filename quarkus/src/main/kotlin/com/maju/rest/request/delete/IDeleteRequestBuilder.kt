package com.maju.rest.request.delete

import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.get.GetRequestBuilder
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.request.get.IGetRequestBuilder

interface IDeleteRequestBuilder : IBaseRequestBuilder {
    fun contentType(contentType: String): IDeleteRequestBuilder

    override fun build(): IDeleteRequest
}

class DeleteRequestBuilder(override val request: IDeleteRequest = DeleteRequest()) : IDeleteRequestBuilder,
    GetRequestBuilder(request) {

    companion object {
        fun create(path: String): IDeleteRequestBuilder = DeleteRequestBuilder().apply { path(path) }
    }

    override fun contentType(contentType: String): IDeleteRequestBuilder = apply {
        request.contentType = contentType
    }

    override fun build(): IDeleteRequest {
        return request
    }

}

fun RestRequestFactory.delete(path: String = "", block: IDeleteRequestBuilder.() -> IRequestBuilder): IDeleteRequest {
    val getRequestBuilder = DeleteRequestBuilder.create(path)
    block.invoke(getRequestBuilder)
    return getRequestBuilder.build()
}