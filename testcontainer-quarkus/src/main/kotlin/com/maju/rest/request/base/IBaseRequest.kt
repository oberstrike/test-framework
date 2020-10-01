package com.maju.rest.request.base

import com.maju.rest.request.auth.RestRequestAuth

interface IBaseRequest {
    var path: String
    var restRequestAuth: RestRequestAuth<*>?
    var params: Map<String, *>?
    var headers: Map<String, String>?
    var cookies: Map<String, *>?

}

interface IRequestBuilder {
    fun build(): IBaseRequest
}

interface IBaseRequestBuilder: IRequestBuilder {
    fun path(path: String): IBaseRequestBuilder
    fun auth(restRequestAuth: RestRequestAuth<*>): IBaseRequestBuilder
    fun params(params: Map<String, *>): IBaseRequestBuilder
    fun headers(headers: Map<String, String>): IBaseRequestBuilder
    fun param(params: Pair<String, *>): IBaseRequestBuilder
}
