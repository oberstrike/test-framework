package com.maju.rest.request.post

import com.maju.rest.request.base.IBaseRequestBuilder
import com.maju.rest.request.base.IRequestBuilder
import com.maju.rest.request.get.GetRequestBuilder
import com.maju.rest.request.get.IGetRequestBuilder
import io.restassured.specification.MultiPartSpecification

interface IPostRequestBuilder : IGetRequestBuilder {
    fun contentType(contentType: String): IPostRequestBuilder

    fun body(body: String): IPostRequestBuilder

    fun file(multiPartSpecification: MultiPartSpecification): IPostRequestBuilder

    override fun build(): IPostRequest

}


open class PostRequestBuilder : IPostRequestBuilder,
    GetRequestBuilder() {

    var contentType: String? = null

    var body: String? = null

    var multipartFile: MultiPartSpecification? = null

    companion object {
        fun create(): IPostRequestBuilder = PostRequestBuilder()
    }


    override fun contentType(contentType: String): IPostRequestBuilder = apply {
        this.contentType = contentType
    }

    override fun body(body: String): IPostRequestBuilder = apply {
        this.body = body
    }

    override fun file(multiPartSpecification: MultiPartSpecification) = apply {
        this.multipartFile = multiPartSpecification
    }

    override fun build(): IPostRequest {
        return PostRequest(path).let {
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

fun post(block: IPostRequestBuilder.() -> IRequestBuilder): IPostRequest {
    val postRequestBuilder = PostRequestBuilder.create()
    block.invoke(postRequestBuilder)
    return postRequestBuilder.build()
}
