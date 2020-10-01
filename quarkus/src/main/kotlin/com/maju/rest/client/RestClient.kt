package com.maju.rest.client

import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.delete.DeleteRequestHandler
import com.maju.rest.request.delete.IDeleteRequest
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.request.pach.IPatchRequest
import com.maju.rest.request.pach.PatchRequestHandler
import com.maju.rest.request.post.IPostRequest
import com.maju.rest.request.post.PostRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.RequestSpecification

interface IRestClient {
    fun get(request: IGetRequest): RestResponse
    fun post(request: IPostRequest): RestResponse
    fun patch(request: IPatchRequest): RestResponse
    fun delete(request: IDeleteRequest): RestResponse
}

class RestClient private constructor(
    private val basePath: String,
    private val port: Int
) : IRestClient {

    companion object {
        fun create(port: Int, basePath: String = ""): IRestClient = RestClient(basePath, port)
    }

    private val getRequestHandler = GetRequestHandler()

    private val postRequestHandler = PostRequestHandler()

    private val deleteRequestHandler = DeleteRequestHandler()

    private val patchRequestHandler = PatchRequestHandler()

    override fun delete(request: IDeleteRequest): RestResponse {
        val requestSpecification = createRequest()
        deleteRequestHandler.onRequestCreate(requestSpecification, request)
        return RestResponse(requestSpecification.delete(request.path))
    }

    override fun get(request: IGetRequest): RestResponse {
        val requestSpecification = createRequest()
        getRequestHandler.onRequestCreate(requestSpecification, request)
        return RestResponse(requestSpecification.get(request.path))
    }

    override fun patch(request: IPatchRequest): RestResponse {
        val requestSpecification = createRequest()
        patchRequestHandler.onRequestCreate(requestSpecification, request)
        return RestResponse(requestSpecification.patch(request.path))
    }

    override fun post(request: IPostRequest): RestResponse {
        val requestSpecification = createRequest()
        postRequestHandler.onRequestCreate(requestSpecification, request)
        return RestResponse(requestSpecification.post(request.path))
    }

    private fun createRequest(): RequestSpecification {
        return Given {

            basePath(basePath)
            log().all()
            port(port)
        }
    }


    interface OnRequestCreateHandler<T : IBaseRequest> {

        fun onRequestCreate(requestSpecification: RequestSpecification, request: T): RequestSpecification
    }
}

