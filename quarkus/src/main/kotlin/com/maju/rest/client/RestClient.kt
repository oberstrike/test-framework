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
import com.maju.rest.request.put.IPutRequest
import com.maju.rest.request.put.PutRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.RequestSpecification
import org.testcontainers.shaded.okhttp3.Response

interface IRestClient {
    fun <T : IBaseRequest> send(request: T): RestResponse
}

interface RestClientConfig {
    val basePath: String
    val port: Int
}


class RestClient private constructor(
    config: RestClientConfig
) : IRestClient {

    private val basePath by lazy { config.basePath }
    private val port by lazy { config.port }

    private val requestHandler = RequestHandler<IBaseRequest>()

    companion object {
        fun create(
            config: RestClientConfig
        ): IRestClient = RestClient(config)
    }

    override fun <T : IBaseRequest> send(request: T): RestResponse {
        val requestSpecification = createRequest()
        return requestHandler.onRequestCreate(requestSpecification, request)
    }

    private fun createRequest(): RequestSpecification {
        return Given {
            basePath(basePath)
            log().all()
            port(port)
        }
    }

    interface OnRequestCreateHandler<T : IBaseRequest> {
        fun onRequestCreate(
            requestSpecification: RequestSpecification,
            request: T
        ): RestResponse
    }
}

