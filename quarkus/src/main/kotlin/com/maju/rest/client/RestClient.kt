package com.maju.rest.client

import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.response.RestResponse
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


    private val requestOnSendHandler by lazy { RequestOnSendHandler<IBaseRequest>(
        config.basePath,
        config.port
    ) }

    companion object {
        fun create(
            config: RestClientConfig
        ): IRestClient = RestClient(config)
    }

    override fun <T : IBaseRequest> send(request: T): RestResponse {
        return requestOnSendHandler.onSend( request)
    }


    interface OnRequestCreateHandler<T : IBaseRequest> {
        fun onRequestCreate(
            request: T,
            port: Int
        ): RequestSpecification
    }

    interface OnSendHandler<T : IBaseRequest> {
        fun onSend(request: T): RestResponse
    }
}

