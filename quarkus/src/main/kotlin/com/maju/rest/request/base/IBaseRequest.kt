package com.maju.rest.request.base

import com.maju.rest.request.auth.IRequestAuth
import io.restassured.specification.RequestSpecification

interface IBaseRequest {
    val path: String
    var requestAuth: IRequestAuth<*>?
    var params: Map<String, *>?
    var headers: Map<String, String>?
    var cookies: Map<String, *>?
    val requestSpecification: RequestSpecification

}

interface IRequestBuilder {
    fun build(): IBaseRequest
}

interface IBaseRequestBuilder: IRequestBuilder {
    fun auth(requestAuth: IRequestAuth<*>): IBaseRequestBuilder
    fun params(params: Map<String, *>): IBaseRequestBuilder
    fun headers(headers: Map<String, String>): IBaseRequestBuilder
    fun param(params: Pair<String, *>): IBaseRequestBuilder
    fun path(path: String): IBaseRequestBuilder
}
