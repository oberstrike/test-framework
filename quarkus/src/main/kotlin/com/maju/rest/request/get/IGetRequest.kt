package com.maju.rest.request.get

import com.maju.rest.client.RestClient
import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.auth.RequestAuth
import com.maju.rest.response.RestResponse
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification

interface IGetRequest : IBaseRequest

open class GetRequest(
    override val path: String
) : IGetRequest {

    override var requestAuth: IRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override var headers: Map<String, String>? = null

    override var cookies: Map<String, *>? = null

    override val requestSpecification: RequestSpecification
        get() = Given { log().all() }

}


class GetRequestHandler(private val isStrict: Boolean = true) : RestClient.OnRequestCreateHandler<IGetRequest> {

    override fun onRequestCreate(request: IGetRequest, port: Int): RequestSpecification {

        return request.requestSpecification.apply {
            if (request.requestAuth != null)
                applyAuth(request.requestAuth!!)
            if (request.headers != null)
                headers(request.headers)
            if (request.cookies != null)
                cookies(request.cookies)
            if (isStrict && request.params != null) params(request.params)

            port(port)
        }

    }

    private fun RequestSpecification.applyAuth(auth: IRequestAuth<*>): RequestSpecification? {
        return when (auth) {
            is RequestAuth.BasicRequestAuth -> auth().preemptive().basic(auth.username, auth.password)
            is RequestAuth.BearerRequestAuth -> auth().preemptive().oauth2(auth.bearerToken)
            else -> this
        }
    }

}
