package com.maju.rest.request.get

import com.maju.rest.client.RestClient
import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.auth.RequestAuth
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification

interface IGetRequest : IBaseRequest

open class GetRequest : IGetRequest {

    override lateinit var path: String

    override var requestAuth: IRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override var headers: Map<String, String>? = null

    override var cookies: Map<String, *>? = null
}


class GetRequestHandler(private val stricted: Boolean = true) : RestClient.OnRequestCreateHandler<IGetRequest> {

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IGetRequest
    ): RestResponse {

        return requestSpecification.apply {
            if (request.requestAuth != null)
                applyAuth(request.requestAuth!!)
            if (request.headers != null)
                headers(request.headers)
            if (request.cookies != null)
                cookies(request.cookies)

            //contentType(ContentType.fromContentType(""))
            if (stricted && request.params != null) params(request.params)
        }.let {
            RestResponse(it.get(request.path))
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
