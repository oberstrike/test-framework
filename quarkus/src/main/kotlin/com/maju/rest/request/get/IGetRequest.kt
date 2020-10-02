package com.maju.rest.request.get

import com.maju.rest.client.RestClient
import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.auth.BasicRestRequestAuth
import com.maju.rest.request.auth.BearerRestRequestAuth
import com.maju.rest.request.auth.RestRequestAuth
import io.restassured.specification.RequestSpecification

interface IGetRequest: IBaseRequest

open class GetRequest : IGetRequest {

    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override var headers: Map<String, String>? = null

    override var cookies: Map<String, *>? = null
}



class GetRequestHandler(private val stricted: Boolean = true) : RestClient.OnRequestCreateHandler<IGetRequest> {


    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IGetRequest
    ): RequestSpecification {

        return requestSpecification.apply {
            if (request.restRequestAuth != null)
                applyAuth(request.restRequestAuth!!)
            if (request.headers != null)
                headers(request.headers)
            if(request.cookies != null)
                cookies(request.cookies)

            //contentType(ContentType.fromContentType(""))
            if (stricted && request.params != null) params(request.params)
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