package com.maju.rest.request

import com.maju.rest.*
import io.restassured.specification.RequestSpecification

interface IGetRequest {
    val path: String
    val restRequestAuth: RestRequestAuth<*>?
    val params: Map<String, *>?
    val headers: Map<String, String>?

}

class GetRequest : IGetRequest {

    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override val headers: Map<String, String>? = null
}


class GetRequestHandler(private val params: Boolean = true) : RestClient.OnRequestCreateHandler<IGetRequest> {


    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IGetRequest
    ): RequestSpecification {

        return requestSpecification.apply {
            if (request.restRequestAuth != null)
                applyAuth(request.restRequestAuth!!)
            if (request.headers != null)
                headers(request.headers)

            if (params) params(request.params)
        }

    }

    private fun RequestSpecification.applyAuth(auth: RestRequestAuth<*>): RequestSpecification? {
        return when (auth) {
            is BasicRestRequestAuth -> auth().preemptive().basic(auth.username, auth.password)
            is BearerRestRequestAuth -> auth().preemptive().oauth2(auth.bearerToken)
            else -> this
        }
    }

}