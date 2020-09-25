package com.maju.rest

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response

interface IRestClient {
    fun get(request: IRestRequest): Response
    fun post(request: IRestRequest): Response
    fun patch(request: IRestRequest): Response
    fun delete(request: IRestRequest): Response
}

interface IRestRequest {

}

class RestClient : IRestClient {

    override fun delete(request: IRestRequest): Response {
        TODO("Not yet implemented")
    }

    override fun get(request: IRestRequest): Response {
        TODO("Not yet implemented")
    }

    override fun patch(request: IRestRequest): Response {
        TODO("Not yet implemented")
    }

    override fun post(request: IRestRequest): Response {
        TODO("Not yet implemented")
    }


}


abstract class AbstractRestTest {

    abstract var port: Int

    abstract fun toJson(obj: Any): String

    protected fun sendGet(
        path: String,
        auth: Pair<String, String>? = null,
        bearerToken: String? = null,
        params: Map<String, *>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            if (bearerToken != null)
                auth().preemptive().oauth2(bearerToken)
            if (params != null)
                params(params)
            port(port)
            log().all()
        }.When {
            get(path)
        }
    }

    protected fun sendPost(
        path: String,
        body: Any,
        bearerToken: String? = null,
        auth: Pair<String, String>? = null
    ): Response {
        return sendPost(
            path = path,
            body = toJson(body),
            bearerToken = bearerToken,
            auth = auth
        )
    }

    protected fun sendPost(
        path: String,
        body: String,
        bearerToken: String? = null,
        auth: Pair<String, String>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            if (bearerToken != null)
                auth().preemptive().oauth2(bearerToken)
            port(port)
            log().all()
            body(body)
            contentType(ContentType.JSON)
        }.When {
            post(path)
        }
    }

    protected fun sendPatch(
        path: String,
        body: String,
        auth: Pair<String, String>? = null,
        params: Map<String, *>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            port(port)
            if (params != null)
                params(params)
            log().all()
            body(body)
            contentType(ContentType.JSON)
        }.When {
            patch(path)
        }

    }


}