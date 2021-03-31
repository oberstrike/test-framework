package com.maju.rest.client

import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.response.RestResponse
import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.RequestSpecification

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

    private val requestOnSendHandler = RequestOnSendHandler<IBaseRequest>()

    companion object {
        fun create(
            config: RestClientConfig
        ): IRestClient = RestClient(config)
    }

    override fun <T : IBaseRequest> send(request: T): RestResponse {
        val requestSpecification = createRequest()
        return requestOnSendHandler.onSend(requestSpecification, request)
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
        ): RequestSpecification
    }

    interface OnSendHandler<T: IBaseRequest>{
        fun onSend(requestSpecification: RequestSpecification, request: T): RestResponse
    }
}

