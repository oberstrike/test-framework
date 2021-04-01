package com.maju.rest.request.delete

import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.get.GetRequestBuilder

interface IDeleteRequestBuilder : IBaseRequestBuilder {
    fun contentType(contentType: String): IDeleteRequestBuilder

    override fun build(): IDeleteRequest
}

class DeleteRequestBuilder : IDeleteRequestBuilder,
    GetRequestBuilder() {

    private var contentType: String? = null

    companion object {
        fun create(path: String): IDeleteRequestBuilder = DeleteRequestBuilder()
    }

    override fun contentType(contentType: String): IDeleteRequestBuilder = apply {
        this.contentType = contentType
    }

    override fun build(): IDeleteRequest {
        return DeleteRequest(path).let {
            it.params = params
            it.cookies = cookies
            it.headers = headers
            it.requestAuth = requestAuth
            it.contentType = contentType
            it
        }
    }

}

fun delete(path: String = "", block: IDeleteRequestBuilder.() -> IRequestBuilder): IDeleteRequest {
    val getRequestBuilder = DeleteRequestBuilder.create(path)
    block.invoke(getRequestBuilder)
    return getRequestBuilder.build()
}
