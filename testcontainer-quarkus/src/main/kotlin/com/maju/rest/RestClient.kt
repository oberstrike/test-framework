package com.maju.rest

import com.maju.rest.request.*
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


    interface OnRequestCreateHandler<T : IGetRequest> {

        fun onRequestCreate(requestSpecification: RequestSpecification, request: T): RequestSpecification
    }
}

